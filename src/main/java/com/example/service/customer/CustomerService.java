package com.example.service.customer;

import com.example.entity.Account;
import com.example.entity.Customer;
import com.example.entity.GroupCar;
import com.example.entity.UserTransaction;

import java.util.List;
import java.util.Set;

public interface CustomerService {
    void addCustomerToGroupCar(int customerId, int groupId);
    void addCustomer(Customer customer);
    Customer findCustomerById(Integer id);
    Customer getCustomer(int id);
    List<Customer> getAllCustomers();
    void deleteCustomer(int id);
    List<Account> getAllCustomerByGroupId(int groupId);
    List<Customer> getCustomersByGroup(GroupCar groupCar);
    Customer getCustomerByUserTransaction(Set<UserTransaction> userTransactions);
}

