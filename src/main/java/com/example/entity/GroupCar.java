package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "group_car")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "groupId")
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

    @ManyToOne
    @JsonBackReference(value = "driver_detail_group_car")
    @JoinColumn(name="driver_detail_id", nullable=false)
    private DriverDetail driverDetail;

    @OneToMany(mappedBy = "groupCar")
    @JsonManagedReference(value = "group_car_trans")
    private Set<Transaction> transactions;

    @ManyToMany(mappedBy = "groupCars")
//    @JsonManagedReference(value = "customersGroupCars")
    Set<Customer> customers;

}
