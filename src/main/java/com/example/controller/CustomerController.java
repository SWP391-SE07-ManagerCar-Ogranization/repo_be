package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Customer;
import com.example.entity.GroupCar;
import com.example.service.account.OurUserDetailsService;
import com.example.service.customer.CustomerService;
import com.example.service.groupcar.GroupCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    GroupCarService groupCarService;
    @Autowired
    OurUserDetailsService ourUserDetailsService;


    @PostMapping("/public/addCustomer/{customerId}/{groupId}")
    public ResponseEntity<?> addCustomer(@PathVariable int customerId, @PathVariable int groupId) {
        try {
            customerService.addCustomerToGroupCar(customerId, groupId);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/public/getAccountsByGroupId/{groupId}")
    public List<Account> getAccountsByGroupId(@PathVariable Integer groupId){
        List<Account> accounts = new ArrayList<>();
        List<Customer> customers = customerService.getCustomersByGroup(groupCarService.getGroupCarById(groupId));
        for (Customer customer : customers) {
            accounts.add(ourUserDetailsService.findById(customer.getId()));
        }
        return accounts;
    }
}
