package com.example.controller;

import com.example.entity.GroupCar;
import com.example.service.groupcar.GroupCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
public class GroupCarController {
    @Autowired
    private GroupCarService service;

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
}
