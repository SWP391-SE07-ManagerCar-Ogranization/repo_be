package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "driver_detail")
@Entity
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DriverDetail {
    @Id
    @Column(name = "driver_detail_id")
    private Integer id;
    private String driverLicence;
    private String vehicleNumber;
    private double rating;
    private Integer totalRating;
    private boolean workingStatus;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "driver",
            joinColumns = @JoinColumn(name = "driver_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_type_id"))
    Set<DriverType> driver;

    @OneToMany(mappedBy = "driverDetail")
    @JsonBackReference(value = "driver_detail_invoice")
    private Set<Invoice> invoices;

    @OneToMany(mappedBy = "driverDetail")
    @JsonBackReference(value = "driver_detail_group_car")
    private Set<GroupCar> groupCars;

//    @OneToMany(mappedBy = "driverDetail")
//    @JsonManagedReference(value = "driver_detail_feedback")
//    private Set<Feedback> feedbacks;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference(value = "driver_detail_account")
    @MapsId
    @JoinColumn(name = "driver_detail_id")
    private Account account;

}
