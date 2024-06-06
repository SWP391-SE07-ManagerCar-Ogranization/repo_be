package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Table(name = "transaction")
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "@transactionId")
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;
    private boolean transactionStatus;
    private Date createAt;
    private double amount;

    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Invoice invoice;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="group_car_id", nullable=false)
    private GroupCar groupCar;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="driver_detail_id", nullable=false)
    private DriverDetail driverDetail;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="payment_method_id", nullable=false)
    private PaymentMethod paymentMethod;
}
