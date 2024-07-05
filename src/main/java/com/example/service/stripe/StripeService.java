package com.example.service.stripe;

import com.example.dto.ChargeRequest;
import com.example.dto.TransferRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.apiKey}")
    private String apiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }

    public PaymentIntent createPaymentIntent(int amount) throws Exception {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long) amount)
                        .setCurrency("usd")
                        .addPaymentMethodType("card")
                        .build();

        return PaymentIntent.create(params);
    }
    public String initiateWithdrawal(int amount, String currency) {
        Stripe.apiKey = apiKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);
        params.put("method", "standard");

        try {
            Payout payout = Payout.create(params);
            return payout.toJson();
        } catch (StripeException e) {
            return e.getMessage();
        }
    }

    public Transfer createTransfer(TransferRequest request) throws StripeException {
        Stripe.apiKey = this.apiKey;

        TransferCreateParams params = TransferCreateParams.builder()
                .setAmount(request.getAmount())
                .setCurrency(request.getCurrency())
                .setDestination(request.getDestinationAccountId())
                .build();

        return Transfer.create(params);
    }
    public ChargeRequest createStripeAccount(String email) throws StripeException {
        AccountCreateParams params = AccountCreateParams.builder()
                .setType(AccountCreateParams.Type.EXPRESS)
                .setEmail(email)
                .setTosAcceptance(
                        AccountCreateParams.TosAcceptance.builder()
                                .setDate(1609798905L)
                                .setIp("8.8.8.8")
                                .build()
                )
                .build();

        Account account = Account.create(params);

        ChargeRequest dto = new ChargeRequest();
        dto.setId(account.getId());
        dto.setEmail(account.getEmail());

        return dto;
    }

    public String createAccountLink(String accountId, String redirectUrl) throws StripeException {
        AccountLinkCreateParams params = AccountLinkCreateParams.builder()
                .setAccount(accountId)
                .setRefreshUrl(redirectUrl)
                .setReturnUrl(redirectUrl)
                .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                .build();

        AccountLink accountLink = AccountLink.create(params);
        return accountLink.getUrl();
    }
}

