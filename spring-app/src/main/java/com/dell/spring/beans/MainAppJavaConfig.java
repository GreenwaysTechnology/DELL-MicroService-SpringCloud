package com.dell.spring.beans;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAppJavaConfig {
    public static void main(String[] args) {
        ApplicationContext ctx=new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(ctx.getBean(HelloWorld.class).sayHello());
    }
}
