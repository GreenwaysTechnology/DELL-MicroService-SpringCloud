package com.dell.microservice.rest.api;

import lombok.Data;

@Data
public class Message {
    private long id;
    private String content;

    public Message(long id, String content) {
        this.id = id;
        this.content = content;
    }
}
