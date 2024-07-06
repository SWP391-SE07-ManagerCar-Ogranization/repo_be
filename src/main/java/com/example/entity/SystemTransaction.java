package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "system_transaction")
@Entity
public class SystemTransaction {
    @Id
    @Column(name = "system_transaction_id")
    private String systemTransactionId;
    private String content;
    private boolean status;
    private Date createAt;
    private double amount;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
}
