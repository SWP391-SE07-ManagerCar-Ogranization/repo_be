package com.example.service.coupon;

import com.example.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getAllCustomer ();
    Optional<Customer> getCustomerById(int customerId);
}
