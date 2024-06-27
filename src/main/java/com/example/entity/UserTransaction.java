package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_transaction")
@Entity
public class UserTransaction {
    @Id
    @Column(name = "user_transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;
    private boolean transactionStatus;
    private Date createAt;
    private double amount;

    @OneToOne(mappedBy = "userTransaction", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonBackReference(value = "transaction_invoice")
    private Invoice invoice;

    @ManyToOne
    @JsonBackReference(value = "group_car_trans")
    @JoinColumn(name="group_car_id", nullable=true)
    private GroupCar groupCar;

    @ManyToOne
    @JsonBackReference(value = "customer_trans")
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @ManyToOne
    @JsonBackReference(value = "driver_detail_trans")
    @JoinColumn(name="driver_detail_id", nullable=false)
    private DriverDetail driverDetail;

    @ManyToOne
    @JsonBackReference(value = "payment_method")
    @JoinColumn(name="payment_method_id", nullable=false)
    private PaymentMethod paymentMethod;
    public UserTransaction(Date createAt, double amount,
                           Customer customer, DriverDetail driverDetail, PaymentMethod paymentMethod, Invoice invoice) {
        this.transactionStatus = false;
        this.createAt = createAt;
        this.amount = amount;
        this.customer = customer;
        this.driverDetail = driverDetail;
        this.paymentMethod = paymentMethod;
        this.invoice = invoice;
    }
    public UserTransaction(Date createAt, double amount,
                           Customer customer, DriverDetail driverDetail, PaymentMethod paymentMethod) {
        this.transactionStatus = false;
        this.createAt = createAt;
        this.amount = amount;
        this.customer = customer;
        this.driverDetail = driverDetail;
        this.paymentMethod = paymentMethod;
    }
}
