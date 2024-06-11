package com.example.service.customer;

import com.example.entity.Customer;
import com.example.repository.CustomerRepository;

import java.util.List;

public interface CustomerService {
    Customer getCustomer(int id);
    List<Customer> getAllCustomers();
    Customer saveCustomer(Customer customer);
    void deleteCustomer(int id);

}
