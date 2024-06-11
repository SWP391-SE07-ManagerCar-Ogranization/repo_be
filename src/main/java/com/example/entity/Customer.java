package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer {
    @Id
    @Column(name = "customer_id")
    private Integer id;
    private int customerPoint;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "group_cars_join",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "group_car_id"))
    @JsonManagedReference(value = "customersGroupCars")
    private Set<GroupCar> groupCars;



    @OneToMany(mappedBy = "customer")
    @JsonBackReference(value = "customers_feedback")
    private Set<Feedback> feedbacks;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference(value = "customer_coupon")
    private Set<Coupon> coupons;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference(value = "customer_invoice")
    private Set<Invoice> invoices;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference(value = "customer_trans")
    private Set<Transaction> transactions;

    @OneToOne
    @JsonManagedReference(value = "account_customer")
    @MapsId
    @JoinColumn(name = "customer_id")
    private Account account;

}
