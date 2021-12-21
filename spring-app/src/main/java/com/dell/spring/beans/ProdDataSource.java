package com.dell.spring.beans;
import org.springframework.stereotype.Component;

@Component
public class ProdDataSource {

    public ProdDataSource() {
        System.out.println("Production datasource");
    }

    public String getProdDataSource() {
        return "production data source";
    }
}