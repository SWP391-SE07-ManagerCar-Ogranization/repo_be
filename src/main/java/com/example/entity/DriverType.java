package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "driver_type")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "driverTypeId")
public class DriverType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverTypeId;
    private String driverTypeName;
    @ManyToMany(mappedBy = "driver")
            @JsonBackReference
    Set<DriverDetail> driverDetails;
}
