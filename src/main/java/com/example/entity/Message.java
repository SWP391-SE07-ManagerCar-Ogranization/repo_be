package com.example.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer messageId;

    @Lob
    private String content;

    private Date createdAt;

    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonManagedReference(value = "customer_message")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "group_car_id")
    @JsonManagedReference(value = "group_car_message")
    private GroupCar groupCar;
}
