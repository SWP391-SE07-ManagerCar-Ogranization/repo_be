package com.example.service.customer;

import com.example.entity.Customer;
import java.util.List;

public interface CustomerService {
    void addCustomerToGroupCar(int customerId, int groupId);
    Customer addCustomer(Customer customer);
    Customer findCustomerById(Integer id);
    Customer getCustomer(int id);
    List<Customer> getAllCustomers();
    Customer saveCustomer(Customer customer);
    void deleteCustomer(int id);
}
