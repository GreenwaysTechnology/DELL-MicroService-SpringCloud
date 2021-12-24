package com.dell.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ReactiveMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveMicroserviceApplication.class, args);
	}

	@GetMapping("/")
	public String home(){
		return "Reactive MicroService";
	}
}
