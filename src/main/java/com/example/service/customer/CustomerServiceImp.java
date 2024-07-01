package com.example.service.customer;

import com.example.entity.Customer;
import com.example.entity.GroupCar;
import com.example.repository.CustomerRepository;
import com.example.service.groupcar.GroupCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    GroupCarService groupCarService;
    @Override
    public void addCustomerToGroupCar(int customerId, int groupId) {
        GroupCar groupCar = groupCarService.getGroupCarById(groupId);
        int quantity = groupCar.getCustomers().size();
        if(groupCar.getCapacity()>quantity) {
            customerRepository.GroupCarJoin(customerId, groupId);
            System.out.println("add add customer successfully");;
        }else{
            System.out.println("gr full");;
        }
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
