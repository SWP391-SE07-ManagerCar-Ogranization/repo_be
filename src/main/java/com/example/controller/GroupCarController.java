package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Customer;
import com.example.entity.DriverDetail;
import com.example.entity.GroupCar;
import com.example.service.DriverDetail.DriverDetailServiceImp;
import com.example.service.account.OurUserDetailsService;
import com.example.service.groupcar.GroupCarService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class GroupCarController {
    @Autowired
    private GroupCarService service;

    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    @Autowired
    private DriverDetailServiceImp driverDetailServiceImp;
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
        service.saveGroupCar(groupCar);
        return groupCar;
    }

    @PostMapping("/public/addGroupCars")
    public List<GroupCar> addGroupCar(@RequestBody List<GroupCar> groupCars) {
        return service.saveGroupCars(groupCars);
    }

    @GetMapping("/public/groupCars")
    public List<GroupCar> getGroupCars() {
        return service.getGroupCars();
    }
    @GetMapping("/public/groupCarsByStartPoint/{startPoint}")
    public List<GroupCar> getGroupCarsByStartPoint(@PathVariable String startPoint) {
        return service.getGroupCarsByStartPoint(startPoint);
    }
    @GetMapping("/public/groupCarsByEndPoint/{endPoint}")
    public List<GroupCar> getGroupCarsByEndPoint(@PathVariable String endPoint) {
        return service.getGroupCarsByEndPoint(endPoint);
    }

    @GetMapping("/public/groupCarById/{id}")
    public GroupCar getGroupCarById(@PathVariable int id) {
        return service.getGroupCarById(id);
    }

    @GetMapping("/public/groupCar/{name}")
    public GroupCar getGroupCarByName(@PathVariable String name) {
        return service.getGroupCarByGroupName(name);
    }


    @PutMapping("/public/update")
    public GroupCar updateGroupCar(@RequestBody GroupCar groupCar) {
        return service.updateGroupCar(groupCar);
    }

    @DeleteMapping("/public/delete/{id}")
    public String deleteGroupCar(@PathVariable int id) {
        return service.deleteGroupCar(id);
    }

    @GetMapping("/public/groupCarsByCustomerId/{id}")
    public List<GroupCar> getGroupCarsByCustomerId(@PathVariable int id) {
        return service.getGroupCarsByCustomerId(id);
    }
//    @GetMapping("/public/getAccountOfDriverDetailByGroupId/{id}")
//    public Account getAccountOfDriverDetailByGroupId(@PathVariable int id) {
//        DriverDetail driverDetail = driverDetailServiceImp.getDriverDetailByGroup(id);
//        return ourUserDetailsService.getAccountByDriverDetail(driverDetail);
//    }

    @GetMapping("/public/getAccountOfDriverDetailByGroupId/{id}")
    public Account getAccountOfDriverDetailByGroupId(@PathVariable int id) {
        GroupCar groupCar = service.getGroupCarById(id);
        DriverDetail  driverDetail = groupCar.getDriverDetail();
        Account account = driverDetail.getAccount();
        return account;
    }

}
