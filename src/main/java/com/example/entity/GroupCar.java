package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "group_car")
@Data
public class GroupCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_car_id")
    private Integer groupId;
    private String groupName;
    private int capacity;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String startPoint;
    private String endPoint;
    private boolean isFinish;
    private LocalDateTime timeStart;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="driver_detail_id", nullable=false)
    private DriverDetail driverDetail;

    @JsonManagedReference
    @OneToMany(mappedBy = "groupCar")
    private Set<Transaction> transactions;

    @ManyToMany(mappedBy = "groupCars")
    Set<Customer> customers;

}
