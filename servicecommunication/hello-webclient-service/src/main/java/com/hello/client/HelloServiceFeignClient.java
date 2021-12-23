package com.hello.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

//hello-service is name of the service from HelloService
@FeignClient("hello-service")
public interface HelloServiceFeignClient {
    //api mapper
    @GetMapping("/hello")
    ResponseEntity<Hello> hello();
}
