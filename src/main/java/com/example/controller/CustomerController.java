package com.example.controller;

import com.example.entity.Account;
import com.example.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/get-all-customer/group-car-id/{id}")
    public ResponseEntity<List<Account>> getAllCustomersByGroupCarId(@PathVariable Integer id) {
        return ResponseEntity.ok( customerService.getAllCustomerByGroupId(id));
    }

}
