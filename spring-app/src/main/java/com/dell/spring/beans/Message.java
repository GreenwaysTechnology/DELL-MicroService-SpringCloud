package com.dell.spring.beans;

import org.springframework.stereotype.Component;

@Component
public class Message {
    public String sayHello(){
        return "Hello";
    }
}
