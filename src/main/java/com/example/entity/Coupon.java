package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "coupon")
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer couponId;
    private String couponName;
    private double couponValue;
    private int couponQuantity;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable=false)
    private Customer customer;
}
