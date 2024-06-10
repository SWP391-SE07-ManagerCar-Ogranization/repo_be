package com.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeRequest {
    public enum Currency {
        eur, usd;
    }
//    private String description;
    private int amount;
    private Currency currency;
//    private String stripeEmail;
//    private String stripeToken;
}
