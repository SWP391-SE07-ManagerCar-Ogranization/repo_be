package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {
    private Long amount;
    private String currency;
    private String destinationAccountId;
}
