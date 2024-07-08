package com.example.service.transaction;

import com.example.entity.*;

import java.time.LocalDateTime;
import java.util.List;
public interface UserTransactionService {
    UserTransaction add (UserTransaction userTransaction);

    void delete(UserTransaction userTransaction);

    UserTransaction update(UserTransaction userTransaction);

    List<UserTransaction> getAll();

    UserTransaction getById(Integer id);
    UserTransaction getByCreateAt(LocalDateTime creatAt);

//    GroupCar getGroupCarById(Integer groupCarId);
    double calculateMoneyByDistance(double distance);
    double calculateMoneyByDistanceAndDriverType(double distance, DriverType type);
    List<UserTransaction> findAllByDriverDetail(DriverDetail driverDetail);

    void addTransactionWithGroupCar(GroupCar groupCar);
    UserTransaction findByCustomerAndGroup(Customer customer, GroupCar groupCar);
}
