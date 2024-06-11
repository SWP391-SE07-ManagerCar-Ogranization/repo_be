package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "feedback")
@Data
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;
    private String feedbackContent;
    private Date createAt;
    private Date updateAt;

    @ManyToOne
    @JsonManagedReference(value = "customers_feedback")
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @ManyToOne
    @JsonManagedReference(value = "driver_detail_feedback")
    @JoinColumn(name="driver_detail_id", nullable=false)
    private DriverDetail driverDetail;

    public Feedback(Integer feedbackId, String feedbackContent, Date createAt, Date updateAt, Customer customer, DriverDetail driverDetail) {
        this.feedbackId = feedbackId;
        this.feedbackContent = feedbackContent;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.customer = customer;
        this.driverDetail = driverDetail;
    }

    public Feedback() {

    }
}
