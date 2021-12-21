package com.dell.spring.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloWorld {
    @Autowired
    private Message message;

    public HelloWorld(){

    }
    public HelloWorld(Message message){
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String sayHello(){
        return this.message.sayHello();
    }
}
