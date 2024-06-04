package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "driver_type")
@Data
public class DriverType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverTypeId;
    private String driverTypeName;
    @ManyToMany(mappedBy = "driver")
    Set<DriverDetail> driverDetails;
}
