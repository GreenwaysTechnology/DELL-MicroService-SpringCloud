package com.dell.microservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class GreetingController {

    @Value("${test.greeting}")
    private String message;

    @GetMapping("/message")
    String getMessage() {
        return this.message;
    }
}
