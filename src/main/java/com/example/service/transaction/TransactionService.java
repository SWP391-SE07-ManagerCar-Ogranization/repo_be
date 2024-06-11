package com.example.service.transaction;

import com.example.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction add (Transaction transaction);

    void delete(Transaction transaction);

    Transaction update(Transaction transaction);

    List<Transaction> getAll();

    Transaction getById(Integer id);

//    GroupCar getGroupCarById(Integer groupCarId);


}
