package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Customer;
import com.example.entity.DriverDetail;
import com.example.entity.GroupCar;
import com.example.service.DriverType.DriverTypeService;
import com.example.service.account.OurUserDetailsService;
import com.example.service.customer.CustomerService;
import com.example.service.groupcar.GroupCarService;
import com.example.service.transaction.UserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GroupCarController {

    @Autowired
    private GroupCarService groupCarService;
    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DriverTypeService driverTypeService;
    @Autowired
    private UserTransactionService userTransactionService;
    @PostMapping("/public/group-car/add")
    public GroupCar addGroupCar(@RequestBody GroupCar groupCar) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account accountCustomer = ourUserDetailsService.findByEmail(email);
        LocalDateTime timeNow = LocalDateTime.now();
        groupCar.setCreateAt(timeNow);
        Set<Customer> customers = new HashSet<>();
        customers.add(accountCustomer.getCustomer());

        groupCar.setCustomers(customers);
        groupCarService.saveGroupCar(groupCar);
        groupCarService.addCustomerGroupCar(accountCustomer.getAccountId(), groupCar.getGroupId());
        return groupCar;
    }

    @PostMapping("/public/addGroupCars")
    public List<GroupCar> addGroupCar(@RequestBody List<GroupCar> groupCars) {
        return groupCarService.saveGroupCars(groupCars);
    }

    @GetMapping("/public/groupCars")
    public List<GroupCar> getGroupCars() {
        return groupCarService.getGroupCars();
    }
    @GetMapping("/public/groupCarsByStartPoint/{startPoint}")
    public List<GroupCar> getGroupCarsByStartPoint(@PathVariable String startPoint) {
        return groupCarService.getGroupCarsByStartPoint(startPoint);
    }
    @GetMapping("/public/groupCarsByEndPoint/{endPoint}")
    public List<GroupCar> getGroupCarsByEndPoint(@PathVariable String endPoint) {
        return groupCarService.getGroupCarsByEndPoint(endPoint);
    }

    @GetMapping("/public/groupCarById/{id}")
    public GroupCar getGroupCarById(@PathVariable int id) {
        return groupCarService.getGroupCarById(id);
    }

    @GetMapping("/public/groupCar/{name}")
    public GroupCar getGroupCarByName(@PathVariable String name) {
        return groupCarService.getGroupCarByGroupName(name);
    }


    @PutMapping("/public/update")
    public GroupCar updateGroupCar(@RequestBody GroupCar groupCar) {
        return groupCarService.updateGroupCar(groupCar);
    }

    @DeleteMapping("/public/delete/{id}")
    public String deleteGroupCar(@PathVariable int id) {
        return groupCarService.deleteGroupCar(id);
    }

    @GetMapping("/public/groupCarsByCustomerId/{id}")
    public List<GroupCar> getGroupCarsByCustomerId(@PathVariable int id) {
        return groupCarService.getGroupCarsByCustomerId(id);
    }

    @GetMapping("/public/groupCarsByDriverDetailId/{id}")
    public List<GroupCar> getGroupCarsByDriverDetailId(@PathVariable int id) {
        return groupCarService.getGroupCarsByDriverDetailId(id);
    }

    @GetMapping("/public/getAccountOfDriverDetailByGroupId/{id}")
    public Account getAccountOfDriverDetailByGroupId(@PathVariable int id) {
        GroupCar groupCar = groupCarService.getGroupCarById(id);
        DriverDetail  driverDetail = groupCar.getDriverDetail();
        Account account = driverDetail.getAccount();
        return account;
    }

    @PostMapping("/public/addDriverDetailOfGroup/{groupId}/{driverDetailId}")
    public ResponseEntity<?> addDriverDetailOfGroup(@PathVariable Integer groupId, @PathVariable Integer driverDetailId) {
        GroupCar groupCar = groupCarService.getGroupCarById(groupId);
        try {
            if(groupCar.getDriverDetail()==null){
                groupCarService.addDriverDetail(groupId, driverDetailId);
            }
            else{
                return ResponseEntity.badRequest().build();
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/public/deleteGroupCarJoin/{customerId}/{groupCarId}")
    public ResponseEntity<String> deleteGroupCarJoin(@PathVariable int customerId, @PathVariable int groupCarId) {
        try {
            groupCarService.deleteGroupCarJoin(customerId, groupCarId);
            return ResponseEntity.ok("GroupCarJoin deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting GroupCarJoin.");
        }
    }

}
