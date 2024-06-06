package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "group_car")
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "@group_car_id")
public class GroupCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_car_id")
    private Integer groupId;
    private String groupName;
    private int capacity;
    private Date createAt;
    private Date updateAt;
    private String startPoint;
    private String endPoint;
    private boolean isFinish;
    private Date timeStart;

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
