package com.example.service.customer;

import com.example.entity.Customer;
import java.util.List;

public interface CustomerService {
    void addCustomer(Customer customer);
    Customer findCustomerById(Integer id);
    Customer getCustomer(int id);
    List<Customer> getAllCustomers();
    void deleteCustomer(int id);
    void addCustomerToGroupCar(int customerId, int groupId);
}
