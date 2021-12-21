package com.dell.microservice;

import com.dell.microservice.rest.api.dao.CustomerRepository;
import com.dell.microservice.rest.api.entites.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements CommandLineRunner {
    @Autowired
    private CustomerRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.save(new Customer("Jack", "Bauer"));
        repository.save(new Customer("Chloe", "O'Brian"));
        repository.save(new Customer("Kim", "Bauer"));
        repository.save(new Customer("David", "Palmer"));
        repository.save(new Customer("Michelle", "Dessler"));
    }
}
