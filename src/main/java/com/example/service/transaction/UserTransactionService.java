package com.example.service.transaction;

import com.example.entity.UserTransaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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

}
