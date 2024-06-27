package com.example.controller;

import com.example.entity.SystemTransaction;
import com.example.service.transaction.SystemTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/public/wallet")
public class WalletController {
    @Autowired
    SystemTransactionService systemTransactionService;
    @GetMapping("/system-transaction-history")
    public ResponseEntity<List<SystemTransaction>> getListSystemTrans() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return ResponseEntity.ok(systemTransactionService.findAllByEmail(email));
    }
}
