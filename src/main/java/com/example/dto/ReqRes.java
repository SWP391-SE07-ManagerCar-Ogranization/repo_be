package com.example.dto;

import com.example.entity.Account;
import com.example.entity.Role;
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
public class ReqRes {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private Role role;
    private String email;
    private String phone;
    private String password;
    private Account account;
    private String idCard;
    private double accountBalance;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private String image;
    private String dob;
    private String address;
    private Date createdAt;
    private Date updateAt;
    private boolean status;
    private String otp;
    private List<Account> accountList;

}
