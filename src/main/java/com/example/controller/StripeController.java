package com.example.controller;

import com.example.dto.ChargeRequest;
import com.example.dto.ReqRes;
import com.example.exception.ApiRequestException;
import com.example.service.account.UsersManagementService;
import com.example.service.stripe.StripeService;
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
    private static final String SIGNING_SECRET = "whsec_2a1a22cef89f8cc4a53ab4207f8c7c70ae44cbd57a409cc6303210c0e53d34aa";
    @PostMapping("/charge")
    public String createPaymentUrl() throws Exception {
        try {
            String currency = "usd";
            Map<String, String> metadata = new HashMap<>();
            metadata.put("transactionId",1 + "");
            metadata.put("userId", "1");
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:3000/payment/result?state=success")
                    .setCancelUrl("http://localhost:3000/payment/result")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(currency)
                                                    .setUnitAmountDecimal(new BigDecimal(100))
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("nameProduct")
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

                String transactionId = session.getPaymentIntent();
                int userId = Integer.parseInt(session.getMetadata().get("userId"));
                String courseId = session.getMetadata().get("courseId");
                String paymentStatus = session.getPaymentStatus();
                Double amount = (double) session.getAmountTotal();
            }

        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Webhook received successfully");
    }
}
