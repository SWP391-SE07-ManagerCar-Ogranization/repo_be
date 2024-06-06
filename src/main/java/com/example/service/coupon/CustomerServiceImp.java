package com.example.service.coupon;

import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService{
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(int customerId) {
        return customerRepository.findById(customerId);
    }
}
