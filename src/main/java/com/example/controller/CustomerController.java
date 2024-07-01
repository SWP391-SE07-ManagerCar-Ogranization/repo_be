package com.example.controller;

import com.example.entity.GroupCar;
import com.example.service.customer.CustomerService;
import com.example.service.groupcar.GroupCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    CustomerService service;

    @PostMapping("/public/addCustomer/{customerId}/{groupId}")
    public ResponseEntity<?> addCustomer(@PathVariable int customerId, @PathVariable int groupId) {
        try {
                service.addCustomerToGroupCar(customerId, groupId);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
