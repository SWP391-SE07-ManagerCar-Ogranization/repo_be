package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "customer")
@Data
public class Customer {
    @Id
    @Column(name = "customer_id")
    private Integer id;
    private int customerPoint;

    @ManyToMany
    @JoinTable(
            name = "group_cars_join",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "group_car_id"))
    Set<GroupCar> groupCars;

    @JsonManagedReference
    @OneToMany(mappedBy = "customer")
    private Set<Feedback> feedbacks;

    @JsonManagedReference
    @OneToMany(mappedBy = "customer")
    private Set<Coupon> coupons;

    @JsonManagedReference
    @OneToMany(mappedBy = "customer")
    private Set<Invoice> invoices;

    @JsonManagedReference
    @OneToMany(mappedBy = "customer")
    private Set<Transaction> transactions;

    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Account account;

}
