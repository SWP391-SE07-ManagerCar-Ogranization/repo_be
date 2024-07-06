package com.example.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "payment_method")
@Data
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentMethodId;
    private String methodName;

    @OneToMany(mappedBy = "paymentMethod")
    @JsonManagedReference(value = "payment_method")
    private Set<UserTransaction> userTransactions;
}
