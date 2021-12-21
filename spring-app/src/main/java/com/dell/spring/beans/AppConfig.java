package com.dell.spring.beans;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration // beans.xml
@ComponentScan //<context:componentscan />
@Import({HelloWorldConfig.class}) //links multiple configurations
public class AppConfig { }
