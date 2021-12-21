package com.dell.spring.beans;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProfileMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        //set
        ctx.getEnvironment().setActiveProfiles("dev");
        ctx.register(DataSourceConfig.class);
        ctx.refresh();
    }
}
