package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "driver_detail")
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DriverDetail {
    @Id
    @Column(name = "driver_detail_id")
    private Integer id;
    private String driverLicence;
    private String vehicleNumber;
    private double rating;
    private boolean workingStatus;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "driver",
            joinColumns = @JoinColumn(name = "driver_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_type_id"))
    Set<DriverType> driver;

    @OneToMany(mappedBy = "driverDetail")
    @JsonBackReference(value = "driver_detail_invoice")
    private Set<Invoice> invoices;

    @OneToMany(mappedBy = "driverDetail")
    @JsonBackReference(value = "driver_detail_group_car")
    private Set<GroupCar> groupCars;

    @OneToMany(mappedBy = "driverDetail")
    @JsonManagedReference(value = "driver_detail_feedback")
    private Set<Feedback> feedbacks;

    @OneToMany(mappedBy = "driverDetail")
    @JsonBackReference(value = "driver_detail_trans")
    private Set<Transaction> transactions;

    @OneToOne
    @JsonManagedReference(value = "driver_detail_account")
    @MapsId
    @JoinColumn(name = "driver_detail_id")
    private Account account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDriverLicence() {
        return driverLicence;
    }

    public void setDriverLicence(String driverLicence) {
        this.driverLicence = driverLicence;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(boolean workingStatus) {
        this.workingStatus = workingStatus;
    }

    public Set<DriverType> getDriver() {
        return driver;
    }

    public void setDriver(Set<DriverType> driver) {
        this.driver = driver;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<GroupCar> getGroupCars() {
        return groupCars;
    }

    public void setGroupCars(Set<GroupCar> groupCars) {
        this.groupCars = groupCars;
    }

    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
