package com.example.service.transaction;

import com.example.entity.UserTransaction;

import java.util.List;
public interface UserTransactionService {
    UserTransaction add (UserTransaction userTransaction);

    void delete(UserTransaction userTransaction);

    UserTransaction update(UserTransaction userTransaction);

    List<UserTransaction> getAll();

    UserTransaction getById(Integer id);

//    GroupCar getGroupCarById(Integer groupCarId);


}
