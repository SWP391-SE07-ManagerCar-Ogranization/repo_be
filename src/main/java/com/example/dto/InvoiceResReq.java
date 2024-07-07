package com.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;


@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvoiceResReq {
    private Integer invoiceId;
    private Date bookingDate;
    private String startPoint;
    private String endPoint;
    private boolean isFinish;
    private Date timeStart;

    private Integer customerId;
    private String customerName;
    private String customerPhone;

}
