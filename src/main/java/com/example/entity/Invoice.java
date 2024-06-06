package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Table(name = "invoice")
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "@invoiceId")
public class Invoice {
    @Id
    @Column(name = "invoice_id")
    private Integer invoiceId;
    private Date bookingDate;
    private String startPoint;
    private String endPoint;
    private boolean isFinish;
    private Date timeStart;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="driver_detail_id", nullable=false)
    private DriverDetail driverDetail;

    @OneToOne
    @MapsId
    @JoinColumn(name = "invoice_id")
    private Transaction transaction;
}
