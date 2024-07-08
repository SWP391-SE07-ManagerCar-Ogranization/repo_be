package com.example.repository;

import com.example.entity.Customer;
import com.example.entity.DriverDetail;
import com.example.entity.GroupCar;
import com.example.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Integer> {
    UserTransaction findUserTransactionByCreateAt(LocalDateTime createAt);

    List<UserTransaction> findAllByDriverDetail(DriverDetail driverDetail);

    UserTransaction findByCustomerAndGroupCar(Customer customer, GroupCar groupCar);
}
