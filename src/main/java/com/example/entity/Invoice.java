package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private LocalDateTime bookingDate;
    private String startPoint;
    private String endPoint;
    private boolean isFinish;
    private Date timeStart;

    @ManyToOne
    @JsonBackReference(value = "customer_invoice")
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @ManyToOne
    @JsonBackReference(value = "driver_detail_invoice")
    @JoinColumn(name="driver_detail_id", nullable=false)
    private DriverDetail driverDetail;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "invoice_id")
    @JsonBackReference(value = "transaction_invoice")
    private UserTransaction userTransaction;
}
