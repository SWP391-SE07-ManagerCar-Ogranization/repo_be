package com.example.dto;

import com.example.entity.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDto {

    private Integer messageId;
    private String content;
    private Date createAt;
    private Date updatedAt;
    private Integer driverDetailId;
    private Integer customerId;
    private Integer senderId;

}