package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice")
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Integer invoiceId;
    private Date bookingDate;
    private String startPoint;
    private String endPoint;
    private boolean isFinish;
    private Date timeStart;

    @ManyToOne
    @JsonBackReference(value = "customer_invoice")
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne
    @JsonBackReference(value = "driver_detail_invoice")
    @JoinColumn(name="driver_detail_id")
    private DriverDetail driverDetail;

    @OneToOne
    @MapsId
    @JoinColumn(name = "invoice_id")
    @JsonBackReference(value = "transaction_invoice")
    private Transaction transaction;

    public Invoice(Date bookingDate, String startPoint, String endPoint, boolean isFinish, Date timeStart, Customer customer, DriverDetail driverDetail, Transaction transaction) {
        this.bookingDate = bookingDate;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.isFinish = isFinish;
        this.timeStart = timeStart;
        this.customer = customer;
        this.driverDetail = driverDetail;
        this.transaction = transaction;
    }
}
