package com.example.service.customer;

import com.example.entity.Account;
import com.example.entity.Customer;
import com.example.repository.AccountRepository;
import com.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void addCustomerToGroupCar(int customerId, int groupId) {
        customerRepository.GroupCarJoin(customerId, groupId);
    }
    @Override
    public Customer getCustomer(int id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return List.of();
    }

    @Override
    public void deleteCustomer(int id) {

    }
    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public Customer findCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Account> getAllCustomerByGroupId(int groupId) {
        List<Integer> customerIds = customerRepository.getAllCustomerByGroupCarId(groupId);
        List<Account> customerAccounts = new ArrayList<>();
        for (Integer customerId : customerIds) {
            Account account = accountRepository.findById(customerId).orElse(null);
            customerAccounts.add(account);
        }
        return customerAccounts;
    }
}
