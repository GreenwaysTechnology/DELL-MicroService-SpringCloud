package com.dell.microservice.rest.api;

import com.dell.microservice.rest.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class OrderController {

    @Autowired // Dependency Injection
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity findAllOrder() {
        return ResponseEntity.ok(orderService.findAllOrder());
    }

    //Return orders based on critera
    @GetMapping("/orders/{name}") //here @PathVariable is done as di which is eq to @Autowired
    public ResponseEntity findOrderByName(@PathVariable(value = "name") String name) {
        List<String> filteredOrders = orderService.findOrderByName(name);
        return ResponseEntity.ok(filteredOrders);
    }

    //http://localhost:8080/api/customers/filter?ranking=prime
    @GetMapping(value = "/customers/filter")//here RequestParam is also di annotation
    public ResponseEntity filter(@RequestParam(value = "ranking") String ranking) {
        List<String> filteredOrders = orderService.findOrderByName(ranking);
        return ResponseEntity.ok(filteredOrders);
    }

}
