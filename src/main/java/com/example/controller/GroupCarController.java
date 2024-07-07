package com.example.controller;

import com.example.entity.Account;
import com.example.entity.DriverDetail;
import com.example.entity.GroupCar;
import com.example.service.DriverDetail.DriverDetailServiceImp;
import com.example.service.account.OurUserDetailsService;
import com.example.service.groupcar.GroupCarService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class GroupCarController {
    @Autowired
    private GroupCarService service;

    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    @Autowired
    private DriverDetailServiceImp driverDetailServiceImp;
    @PostMapping("/public/addGroupCar")
    public GroupCar addGroupCar(@RequestBody GroupCar groupCar) {
        return service.saveGroupCar(groupCar);
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

    @PostMapping("/public/addDriverDetailOfGroup/{groupId}/{driverDetailId}")
    public ResponseEntity<?> addDriverDetailOfGroup(@PathVariable Integer groupId, @PathVariable Integer driverDetailId) {
        GroupCar groupCar = service.getGroupCarById(groupId);
        try {
            if(groupCar.getDriverDetail()==null){
                service.addDriverDetail(groupId, driverDetailId);
            }
            else{
                return ResponseEntity.badRequest().build();
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
