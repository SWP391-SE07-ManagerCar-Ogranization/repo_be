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

    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Account account;
    public void setAccount(Account account) {
        this.account = account;
        this.account.setCustomer(this); // setting the parent class as the value for the child instance
    }
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
    @JsonManagedReference(value = "customer_coupon")
    private Set<Coupon> coupons;

    @OneToMany(mappedBy = "customer",cascade=CascadeType.ALL)
    @JsonManagedReference(value = "customer_invoice")
    private Set<Invoice> invoices;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference(value = "customer_trans")
    private Set<Transaction> transactions;


}
