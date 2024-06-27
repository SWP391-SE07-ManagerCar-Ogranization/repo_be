package com.example.controller;

import com.example.dto.ChargeRequest;
import com.example.dto.ReqRes;
import com.example.entity.Account;
import com.example.entity.SystemTransaction;
import com.example.exception.ApiRequestException;
import com.example.service.account.OurUserDetailsService;
import com.example.service.account.UsersManagementService;
import com.example.service.stripe.StripeService;
import com.example.service.transaction.SystemTransactionService;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.model.checkout.Session;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/public/payment")
public class StripeController {
    @Value("${stripe.apiKey}")
    private String stripeApiKey;
    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }
    @Autowired
    private UsersManagementService usersManagementService;
    @Autowired
    private SystemTransactionService systemTransactionService;
    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    private static final String SIGNING_SECRET = "whsec_2a1a22cef89f8cc4a53ab4207f8c7c70ae44cbd57a409cc6303210c0e53d34aa";
    @PostMapping("/charge")
    public String createPaymentUrl(@RequestBody ReqRes reqRes) throws Exception {
        try {
            String currency = "vnd";
            Map<String, String> metadata = new HashMap<>();
            metadata.put("transactionId",1 + "");
            metadata.put("userId", String.valueOf(reqRes.getAccount().getAccountId()));
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:3000/payment/result/success")
                    .setCancelUrl("http://localhost:3000/payment/result")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(currency)
                                                    .setUnitAmountDecimal(BigDecimal.valueOf(reqRes.getAmount()))
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(reqRes.getMessage())
                                                                    .build())
                                                    .build())
                                    .build())
                    .putAllMetadata(metadata)
                    .build();

            Session session = Session.create(params);
            return session.getUrl();
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception();
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhookEvent(
            @RequestBody String payload, HttpServletRequest request) {
        Event event = null;
        String header = request.getHeader("Stripe-Signature");
        try {
            event = Webhook.constructEvent(payload, header, SIGNING_SECRET);
            if (event.getType().equals("checkout.session.completed")) {
                @SuppressWarnings("deprecation")
                Session session = (Session) event.getData().getObject();
                Double amount = (double) session.getAmountTotal();
                String content = session.getPaymentStatus();
                String id = session.getId();
                String email = session.getCustomerDetails().getEmail();
                Account account = usersManagementService.getMyInfo(email).getAccount();
                account.setAccountBalance(account.getAccountBalance()+amount);
                ourUserDetailsService.addAccount(account);
                systemTransactionService.add(new SystemTransaction(id,content,true,new Date(),amount, account));
            }

        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Webhook received successfully");
    }
}
