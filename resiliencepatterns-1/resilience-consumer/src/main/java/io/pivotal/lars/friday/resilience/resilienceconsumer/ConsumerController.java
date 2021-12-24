package io.pivotal.lars.friday.resilience.resilienceconsumer;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * ConsumerController
 */
class BackEndService {
    public String doSomething() {
        System.out.println("Hitting services");
        return "Do Something" + Math.random();
    }
}

@RestController
public class ConsumerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerController.class);
    private final RestTemplate restTemplate;
    private final String providerUri;
    private final Bulkhead bulkhead;
    private final CircuitBreaker circuitBreaker;
    private final RateLimiter rateLimiter;
    private final TimeLimiter timeLimiter;
    private final Retry retry;
    private BackEndService backEndService = new BackEndService();

    public ConsumerController(RestTemplate restTemplate, @Value("${provider.uri}") String providerUri,
                              @Value("${maxConcurrent}") int maxConcurrent) {
        this.restTemplate = restTemplate;
        this.providerUri = providerUri;
        this.bulkhead = createBulkHead(maxConcurrent);
        this.circuitBreaker = createCircuitBreaker();
        this.rateLimiter = createRateLimiter();
        this.timeLimiter = createTimeLimiter();
        this.retry = createRetry();

    }

    private Retry createRetry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(5)
                .waitDuration(Duration.ofMillis(10000)).build();
        RetryRegistry registry = RetryRegistry.of(config);
        Retry retry = registry.retry("app-retry");
        retry.getEventPublisher()
                .onRetry(r -> System.out.println("Retrying....." + r.getName() + " " +  r.getNumberOfRetryAttempts()))
                .onSuccess(e -> System.out.println("Retry success!!!" + e.getName()))
                .onError(e -> System.out.println("Retry error" + e.getName()));
        return retry;
    }

    private TimeLimiter createTimeLimiter() {

        TimeLimiterConfig config = TimeLimiterConfig
                .custom().cancelRunningFuture(true)
                .timeoutDuration(Duration.ofMillis(6000)).build();
        TimeLimiter timeLimiter = TimeLimiter.of("TimeLimitter-1", config);

        timeLimiter.getEventPublisher().onSuccess(event -> {
            LOGGER.info("Timeout call is going");
        }).onTimeout(event -> {
            LOGGER.info("Timeout happend");
        }).onError(err -> {
            LOGGER.info("Error " + err);
        });
        return timeLimiter;
    }


    private RateLimiter createRateLimiter() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMillis(10000))
                .limitForPeriod(50)
                .timeoutDuration(Duration.ofMillis(5))
                .build();
// Create registry
        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);

        rateLimiterRegistry.getEventPublisher()
                .onEntryAdded(entryAddedEvent -> {
                    RateLimiter addedRateLimiter = entryAddedEvent.getAddedEntry();
                    LOGGER.info("RateLimiter {} added", addedRateLimiter.getName());
                })
                .onEntryRemoved(entryRemovedEvent -> {
                    RateLimiter removedRateLimiter = entryRemovedEvent.getRemovedEntry();
                    LOGGER.info("RateLimiter {} removed", removedRateLimiter.getName());
                });

// Use registry
        RateLimiter rateLimiterWithDefaultConfig = rateLimiterRegistry
                .rateLimiter("name1");
        return rateLimiterWithDefaultConfig;
    }

    //bulk head configuration
    private Bulkhead createBulkHead(int maxConcurrent) {
        BulkheadConfig bulkheadConfig = BulkheadConfig.custom().maxConcurrentCalls(maxConcurrent).build();
        Bulkhead bulkhead = Bulkhead.of("resilience-provider", bulkheadConfig);
        bulkhead.getEventPublisher()
                .onCallPermitted(event -> LOGGER.info("Call permitted by bulkhead"))
                .onCallRejected(event -> LOGGER.info("Call rejected by bulkhead"))
                .onCallFinished(event -> LOGGER.info("Call Finished by bulkhead"));

        return bulkhead;
    }

    private CircuitBreaker createCircuitBreaker() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom().failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofMillis(20000)).build();
        CircuitBreaker circuitBreaker = CircuitBreaker.of("resilience-provider", circuitBreakerConfig);

        circuitBreaker.getEventPublisher().onSuccess(event -> LOGGER.info("Call success via circuit breaker"))
                .onCallNotPermitted(event -> LOGGER.info("Call denied by circuit breaker"))
                .onError(event -> LOGGER.info("Call failed via circuit breaker"));
        return circuitBreaker;
    }

    @GetMapping()
    public String okay() {
        return "The message was " + restTemplate.getForObject(providerUri, String.class);
    }

    @GetMapping("/retry")
    public String retry() throws Exception {
        CheckedFunction0<String> retryableSupplier = Retry.decorateCheckedSupplier(retry, () -> {
            return "The message was " + restTemplate.getForObject(providerUri + "/error", String.class);
        });
        Try<String> result = Try.of(retryableSupplier).
                recover((throwable) -> "Hello world from recovery function");
        return result.get();
    }

    @GetMapping("/bulkhead")
    public String bulkhead() {
        CheckedFunction0<String> someServiceCall = Bulkhead.decorateCheckedSupplier(bulkhead,
                () -> "The message was " + restTemplate.getForObject(providerUri + "/slow", String.class));

//        Try<String> result = Try.of(someServiceCall).recover((throwable) -> "This is a bulkhead fallback");
//        return result.get();
        // and chain an other function with map
        String result = Try.of(someServiceCall)
                .map(value -> value + "This is extra ").getOrElse("Failed.......");
        System.out.println(result);
        return result;
    }

    @GetMapping("/ratelimiter")
    public String rateLimiting() {
        Supplier<String> supplier = RateLimiter.decorateSupplier(rateLimiter, backEndService::doSomething);
        Try<String> res = Try.ofSupplier(supplier);
        // Try<String> result = Try.ofSupplier(supplier).recover((throwable) -> "This is a RateLimiter fallback");
        // return result.get();
        return res.get();

    }

    @GetMapping("/timeout")
    public String timeout() {
        String result = "";
        try {
            result = timeLimiter.executeFutureSupplier(
                    () -> CompletableFuture.supplyAsync(() -> "The message was " + restTemplate.getForObject(providerUri + "/slow", String.class)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/circuitbreaker")
    public String circuitBreakerFail(@RequestParam boolean shouldFail) {
        if (shouldFail) {
            return callServiceViaCircuitBreaker("/error");
        } else {
            return callServiceViaCircuitBreaker("/");
        }
    }

    private String callServiceViaCircuitBreaker(String uri) {
        CheckedFunction0<String> someServiceCall = CircuitBreaker.decorateCheckedSupplier(circuitBreaker,
                () -> "The message was " + restTemplate.getForObject(providerUri + uri, String.class));
        Try<String> result = Try.of(someServiceCall).recover((throwable) -> "This is a circuit breaker fallback");
        return result.get();
    }
}
