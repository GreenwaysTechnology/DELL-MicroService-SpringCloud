package com.dell.spring.boot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInitalizer implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        //todo: code to initalized: like data source initalization....
        System.out.println("App is initalize");
    }
}
