package com.dell.microservice.rest.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//service -class
@RestController
@RequestMapping("/api")
public class GreetingController {
    //api

    //Object
    @GetMapping("/hello")
    public Message sayGreet() {
        return new Message(1, "Hello");
    }

    //String
    @GetMapping
    //  @RequestMapping(method = RequestMethod.GET, value = "/welcome")
    public String sayWelcome() {
        return "Welcome";
    }

    //POST
    @PostMapping("/save")
    public ResponseEntity postForm() {
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    //PUT
    @PutMapping("/update")
    public ResponseEntity updateForm() {
        return ResponseEntity.status(HttpStatus.OK).body("Updated");
    }

    //Remove
    @DeleteMapping("/remove")
    public ResponseEntity removeForm() {
        return ResponseEntity.status(HttpStatus.OK).body("Removed");
    }

    //ResponseEnity
    @GetMapping("/hai")
    public ResponseEntity sayGreet1() {
        Message message = new Message(2, "Hai");
//        return ResponseEntity.
//                status(HttpStatus.OK)
//                .header("foo", "bar")
//                .body(message);
        return ResponseEntity.ok()
                .header("foo", "bar")
                .body(message);
    }
}
