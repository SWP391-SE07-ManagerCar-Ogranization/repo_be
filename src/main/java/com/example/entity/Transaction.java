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
@Table(name = "transaction")
@Entity
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;
    private boolean transactionStatus;
    private Date createAt;
    private double amount;

    @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Invoice invoice;

    @ManyToOne
    @JsonBackReference(value = "group_car_trans")
    @JoinColumn(name="group_car_id", nullable=true)
    private GroupCar groupCar;

    @ManyToOne
    @JsonBackReference(value = "customer_trans")
    @JoinColumn(name="customer_id", nullable=false)
    private Customer customer;

    @ManyToOne
    @JsonBackReference(value = "driver_detail_trans")
    @JoinColumn(name="driver_detail_id", nullable=false)
    private DriverDetail driverDetail;

    @ManyToOne
    @JsonBackReference(value = "payment_method")
    @JoinColumn(name="payment_method_id", nullable=false)
    private PaymentMethod paymentMethod;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(boolean transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public GroupCar getGroupCar() {
        return groupCar;
    }

    public void setGroupCar(GroupCar groupCar) {
        this.groupCar = groupCar;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public DriverDetail getDriverDetail() {
        return driverDetail;
    }

    public void setDriverDetail(DriverDetail driverDetail) {
        this.driverDetail = driverDetail;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Transaction() {

    }



    public Transaction(Date createAt, double amount,
              Customer customer, DriverDetail driverDetail, PaymentMethod paymentMethod, Invoice invoice) {
        this.transactionStatus = false;
        this.createAt = createAt;
        this.amount = amount;
        this.customer = customer;
        this.driverDetail = driverDetail;
        this.paymentMethod = paymentMethod;
        this.invoice = invoice;
    }
    public Transaction(Date createAt, double amount,
                       Customer customer, DriverDetail driverDetail, PaymentMethod paymentMethod) {
        this.transactionStatus = false;
        this.createAt = createAt;
        this.amount = amount;
        this.customer = customer;
        this.driverDetail = driverDetail;
        this.paymentMethod = paymentMethod;
    }
}
