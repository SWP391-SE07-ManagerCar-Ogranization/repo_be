package com.example.controller;

import com.example.dto.ReqRes;
import com.example.entity.Account;
import com.example.service.account.OurUserDetailsService;
import com.example.service.account.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserManagementController {
    @Autowired
    private UsersManagementService usersManagementService;
    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> register(@RequestBody ReqRes reg){
        return ResponseEntity.ok(usersManagementService.register(reg));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.login(req));
    }
    @PostMapping("/oauth2/login")
    public ResponseEntity<ReqRes> loginOauth2(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.loginOauth2(req));
    }
    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    @GetMapping("/admin/get-all-users")
    public ResponseEntity<ReqRes> getAllUsers(){
        return ResponseEntity.ok(usersManagementService.getAllUsers());

    }

    @GetMapping("/admin/get-users/{userId}")
    public ResponseEntity<ReqRes> getUSerByID(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));

    }

    @PutMapping("/adminuser/update/{userId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Integer userId, @RequestBody Account request){
        return ResponseEntity.ok(usersManagementService.updateUser(userId, request));
    }

    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<ReqRes> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ReqRes response = usersManagementService.getMyInfo(email);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<ReqRes> deleteUSer(@PathVariable Integer userId){
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }

    //Do-Admin
    @GetMapping("/public/get-all-customers")
    public ResponseEntity<List<Account>> getAllCustomers(){
        return ResponseEntity.ok(ourUserDetailsService.getAllAccountByRoleId(1));
    }

    @GetMapping("/public/get-all-drivers")
    public ResponseEntity<List<Account>> getAllDrivers(){
        return ResponseEntity.ok(ourUserDetailsService.getAllAccountByRoleId(3));
    }

    @PutMapping("/public/{id}/update-status")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestParam boolean status){
        return ResponseEntity.ok(ourUserDetailsService.updateStatusById(id,status));
    }




}
