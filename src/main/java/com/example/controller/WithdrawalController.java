package com.example.controller;

import com.example.dto.ChargeRequest;
import com.example.dto.ReqRes;
import com.example.dto.TransferRequest;
import com.example.service.stripe.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WithdrawalController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/public/stripe/withdraw")
    public String withdraw(@RequestParam int amount, @RequestParam String currency) {
        return stripeService.initiateWithdrawal(amount, currency);
    }
    @PostMapping("/create")
    public ResponseEntity<?> createTransfer(@RequestBody TransferRequest transferRequest) {
        try {
            Transfer transfer = stripeService.createTransfer(transferRequest);
            return ResponseEntity.ok(transfer);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/public/create/account")
    public ResponseEntity<ChargeRequest> createTransferAccount(@RequestBody ReqRes reqRes) {
        try {
            return ResponseEntity.ok(stripeService.createStripeAccount(reqRes.getEmail()));
        } catch (StripeException e) {
            e.printStackTrace();
            return null;
        }
    }
//    @PostMapping("/public/stripe/add-bank")
//    public String addBankAccount(
//            @RequestParam String customerId,
//            @RequestParam String accountHolderName,
//            @RequestParam String accountNumber,
//            @RequestParam String routingNumber,
//            @RequestParam String currency) {
//        return stripeService.addBankAccountToCustomer(customerId, accountHolderName, accountNumber, routingNumber, currency);
//    }
}
