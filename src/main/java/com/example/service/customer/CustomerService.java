package com.example.service.customer;

import com.example.entity.Customer;
import com.example.entity.GroupCar;

import java.util.List;

public interface CustomerService {
    void addCustomerToGroupCar(int customerId, int groupId);
    void addCustomer(Customer customer);
    Customer findCustomerById(Integer id);
    Customer getCustomer(int id);
    List<Customer> getAllCustomers();
    void deleteCustomer(int id);
    List<Customer> getCustomersByGroup(GroupCar groupCar);
}

