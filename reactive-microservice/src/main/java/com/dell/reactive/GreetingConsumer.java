package com.dell.reactive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class GreetingConsumer {
    private final WebClient webClient;

    public GreetingConsumer(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8080").build();
    }

    @GetMapping("/webclient")
    public Mono<String> greetConsume() {
        return this.webClient.get()
                .uri("/hello/reactive")
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping("/foo")
    public String foo() {
        return "foo";
    }
}
