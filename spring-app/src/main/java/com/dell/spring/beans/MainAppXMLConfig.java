package com.dell.spring.beans;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainAppXMLConfig {
    public static void main(String[] args) {
        //Create Object via new Operator
//        HelloWorld helloWorld = new HelloWorld();
//        System.out.println(helloWorld.sayHello());

        //Create Object via Spring Container
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        HelloWorld helloWorld1=context.getBean(HelloWorld.class);
        System.out.println(helloWorld1.sayHello());

    }
}
