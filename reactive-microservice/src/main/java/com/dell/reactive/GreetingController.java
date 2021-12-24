package com.dell.reactive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
public class GreetingController {

    @GetMapping("/hello/reactive")
    public Flux<String> sayHello() {
        //streaming 0...N
        return Flux.just("Hello", "World", "How ", "are", "You");
    }

    @GetMapping("/goodbye/reactive")
    public Mono<String> sayGoodBye() {
        //streaming o...1
        return Mono.just("Good Bye Reactor");
    }

    //just is opertor
    @GetMapping("/greet/reactive")
    public Mono<Greeter> sayGreet() {
        return Mono.just(new Greeter("GreetMe"));
    }

    @GetMapping("/hello/reactive/filter")
    public Flux<String> filter() {
        //streaming 0...N
        return Flux
                .just("Hello", "World", "How ", "are", "You")
                .filter(c -> c.contains("Hello"))
                .subscribeOn(Schedulers.immediate()) // run a separate thread of execution
                .map(String::toUpperCase); // operator
    }

}

