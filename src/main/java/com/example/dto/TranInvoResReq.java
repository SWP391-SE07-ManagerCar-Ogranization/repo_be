package com.example.dto;

import com.example.entity.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TranInvoResReq {
    private Date bookingDate;
    private String startPoint;
    private String endPoint;
    private boolean isFinish;
    private Date timeStart;
//    private Customer customer;
    private Account account;
    private DriverDetail driverDetail;
    private double amount;
    private PaymentMethod paymentMethod;
    private DriverType driverType;

}
