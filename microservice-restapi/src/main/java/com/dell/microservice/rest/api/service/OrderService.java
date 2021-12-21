package com.dell.microservice.rest.api.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    List<String> orders = Arrays.asList("Dress", "Phone", "TV");

    public List<String> findAllOrder() {
        return orders;
    }

    //filtered order
    public List<String> findOrderByName(String input) {
        if (input == null) {
            return orders;
        }
        //java streams
        return orders.stream().filter(s -> s.contains(input)).collect(Collectors.toList());
    }
}
