package com.example.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Table(name = "driver_detail")
@Entity
public class DriverDetail {
    @Id
    @Column(name = "driver_detail_id")
    private Integer id;
    private String driverLicence;
    private String vehicleNumber;
    private double rating;
    private boolean workingStatus;
    @ManyToMany
    @JoinTable(
            name = "driver",
            joinColumns = @JoinColumn(name = "driver_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_type_id"))
    Set<DriverType> driver;

    @JsonManagedReference
    @OneToMany(mappedBy = "driverDetail")
    private Set<Invoice> invoices;

    @JsonManagedReference
    @OneToMany(mappedBy = "driverDetail")
    private Set<GroupCar> groupCars;

    @JsonManagedReference
    @OneToMany(mappedBy = "driverDetail")
    private Set<Feedback> feedbacks;

    @JsonManagedReference
    @OneToMany(mappedBy = "driverDetail")
    private Set<Transaction> transactions;

    @OneToOne
    @MapsId
    @JoinColumn(name = "driver_detail_id")
    private Account account;
}
