package com.example.dto;

import com.example.entity.GroupCar;
import com.example.entity.Invoice;
import com.example.entity.UserTransaction;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class InfoBookingForDriver {
    private Invoice invoice;
    private GroupCar groupCar;
    private UserTransaction userTransaction;
    private Set<UserTransaction> userTransactions;
    private String nameCustomer;
    private String nameDriver;
    private Long timeRemaining;
}
