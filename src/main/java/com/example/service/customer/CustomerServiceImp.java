package com.example.service.customer;

import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImp implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public void addCustomerToGroupCar(int customerId, int groupId) {
        customerRepository.GroupCarJoin(customerId, groupId);
    }
}
