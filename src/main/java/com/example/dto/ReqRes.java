package com.example.dto;

import com.example.entity.Account;
import com.example.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private String image;
    private String dob;
    private String address;
    private Date createdAt;
    private Date updateAt;
    private boolean status;
    private List<Account> accountList;

}
