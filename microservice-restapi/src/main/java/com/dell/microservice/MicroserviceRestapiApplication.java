package com.dell.microservice;

import com.dell.microservice.rest.api.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class MicroserviceRestapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceRestapiApplication.class, args);
    }

    @GetMapping
    public Message home() {
        return new Message(0, "Home");
    }

}
