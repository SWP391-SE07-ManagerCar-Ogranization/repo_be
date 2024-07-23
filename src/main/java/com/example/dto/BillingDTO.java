package com.example.dto;

import com.example.entity.Coupon;
import com.example.entity.UserTransaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillingDTO {
    private UserTransaction userTransaction;
    private Coupon coupon;
}
