package com.example.service.customer;

import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomer(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return List.of();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return null;
    }

    @Override
    public void deleteCustomer(int id) {

    }
    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }
}
