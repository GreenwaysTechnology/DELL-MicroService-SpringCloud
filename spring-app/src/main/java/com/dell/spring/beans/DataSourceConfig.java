package com.dell.spring.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DataSourceConfig {

    @Bean
    @Profile("dev")
    public DevDataSource createDevDataSource(){
        return new DevDataSource();
    }
    @Bean
    @Profile("prod")
    public ProdDataSource createProdDataSource(){
        return new ProdDataSource();
    }
}