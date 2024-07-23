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
import org.springframework.web.bind.annotation.*;

@RestController
public class WithdrawalController {

    @Autowired
    private StripeService stripeService;


}
