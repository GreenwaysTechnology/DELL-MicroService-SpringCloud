package com.dell.microservice.rest.api.dao;

import com.dell.microservice.rest.api.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    //todo: Your own data logic: Most of the CURD OPERATIONS ARE DEFAULT
    List<Customer> findByLastName(String lastName);
    Customer findById(long id);
}
