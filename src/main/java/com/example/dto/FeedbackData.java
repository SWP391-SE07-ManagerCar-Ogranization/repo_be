package com.example.dto;

import com.example.entity.Account;
import com.example.entity.Feedback;
import com.example.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedbackData {
//    driver
    private Account driverAccount;
//    end_driver
    private List<Feedback> driverFeedback;
    private Account customerAccount;

}
