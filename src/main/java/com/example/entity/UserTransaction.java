package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private LocalDateTime createAt;
    private double amount;

    @OneToOne(mappedBy = "userTransaction", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "transaction_invoice")
    private Invoice invoice;

    @ManyToOne
    @JsonBackReference(value = "group_car_trans")
    @JoinColumn(name="group_car_id")
    @PrimaryKeyJoinColumn
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
}
