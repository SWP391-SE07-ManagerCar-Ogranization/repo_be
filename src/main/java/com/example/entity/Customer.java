package com.example.entity;

import com.fasterxml.jackson.annotation.*;
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
//    @JsonBackReference(value = "customersGroupCars")
    Set<GroupCar> groupCars;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference(value = "customers_feedback")
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
    @JsonBackReference(value = "account_customer")
    @MapsId
    @JoinColumn(name = "customer_id")
    private Account account;

}
