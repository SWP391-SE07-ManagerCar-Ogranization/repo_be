package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "driver_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "driverTypeId")
public class DriverType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int driverTypeId;
    private String driverTypeName;
    @ManyToMany(mappedBy = "driver")
            @JsonBackReference(value = "driver_detail_type")
    Set<DriverDetail> driverDetails;
}
