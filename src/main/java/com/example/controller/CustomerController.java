package com.example.controller;

import com.example.entity.Customer;
import com.example.entity.Invoice;
import com.example.service.customer.CustomerService;
import com.example.service.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/public")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/getCustomerBy/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Integer id) {
        Customer customer = customerService.getById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
