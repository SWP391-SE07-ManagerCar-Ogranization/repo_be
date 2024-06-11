package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Table(name = "invoice")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @Column(name = "invoice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;
    private Date bookingDate;
    private String startPoint;
    private String endPoint;
    private boolean isFinish;
    private Date timeStart;

    @ManyToOne(cascade=CascadeType.ALL)
    @JsonBackReference(value = "customer_invoice")
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne
    @JsonBackReference(value = "driver_detail_invoice")
    @JoinColumn(name="driver_detail_id")
    private DriverDetail driverDetail;

    @OneToOne
    @MapsId
    @JoinColumn(name = "invoice_id")
    @JsonBackReference(value = "transaction_invoice")
    private Transaction transaction;

    public Invoice(Date bookingDate, String startPoint, String endPoint, boolean finish, Date timeStart, Customer customer, DriverDetail driverDetail, Transaction newTran) {
        this.bookingDate = bookingDate;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.isFinish = finish;
        this.timeStart = timeStart;
        this.customer = customer;
        this.driverDetail = driverDetail;
        this.transaction = newTran;
    }
    public Invoice(Date bookingDate, String startPoint, String endPoint, boolean finish, Date timeStart, DriverDetail driverDetail, Transaction newTran) {
        this.bookingDate = bookingDate;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.isFinish = finish;
        this.timeStart = timeStart;
        this.driverDetail = driverDetail;
        this.transaction = newTran;
    }

//    public Invoice() {
//    }

//    public Invoice(Customer customer, Integer invoiceId, Date bookingDate, String startPoint, String endPoint, boolean isFinish, Date timeStart, DriverDetail driverDetail, Transaction transaction) {
//        this.customer = customer;
//        this.invoiceId = invoiceId;
//        this.bookingDate = bookingDate;
//        this.startPoint = startPoint;
//        this.endPoint = endPoint;
//        this.isFinish = isFinish;
//        this.timeStart = timeStart;
//        this.driverDetail = driverDetail;
//        this.transaction = transaction;
//    }


}
