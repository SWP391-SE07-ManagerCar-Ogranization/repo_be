package com.example.service.customer;

import com.example.entity.Customer;

public interface CustomerService {
    Customer addCustomer(Customer customer);
    Customer findCustomerById(Integer id);
}
