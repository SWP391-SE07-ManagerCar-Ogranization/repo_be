package com.example.controller;

import com.example.entity.Account;
import com.example.entity.DriverDetail;
import com.example.entity.UserTransaction;
import com.example.service.account.OurUserDetailsService;
import com.example.service.transaction.UserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/public/transaction")
public class UserTransactionController {
    @Autowired
    private UserTransactionService userTransactionService;
    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    @PostMapping("/payment")
    public ResponseEntity<?> paymentForDriver(@RequestBody UserTransaction userTransaction) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserTransaction transactionGet = userTransactionService.getById(userTransaction.getTransactionId());
        Account accountCustomer = ourUserDetailsService.findByEmail(email);
        double walletCustomer = accountCustomer.getAccountBalance();
        DriverDetail driverDetail = transactionGet.getDriverDetail();
        Account accountDriver = driverDetail.getAccount();
        double walletDriver = accountDriver.getAccountBalance();
        if(walletCustomer > userTransaction.getAmount()) {
            if(!transactionGet.isTransactionStatus()) {
                accountCustomer.setAccountBalance(walletCustomer - userTransaction.getAmount());
                accountDriver.setAccountBalance(walletDriver + userTransaction.getAmount());
                transactionGet.setTransactionStatus(true);
                userTransactionService.add(transactionGet);
                ourUserDetailsService.addAccount(accountCustomer);
                ourUserDetailsService.addAccount(accountDriver);
                return ResponseEntity.ok("Transaction Successfully");
            } else {
                return ResponseEntity.ok("Transaction had payment");
            }
        }
        return ResponseEntity.ok("Not enough balance to payment");
    }
}
