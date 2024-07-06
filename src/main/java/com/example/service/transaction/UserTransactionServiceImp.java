package com.example.service.transaction;


import com.example.entity.UserTransaction;
import com.example.repository.UserTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserTransactionServiceImp implements UserTransactionService {
    @Autowired
    private UserTransactionRepository userTransactionRepository;

    @Override
    public UserTransaction add(UserTransaction userTransaction) {
        return userTransactionRepository.save(userTransaction);
    }


    @Override
    public void delete(@RequestBody UserTransaction userTransaction) {
        userTransactionRepository.delete(userTransaction);
    }

    @Override
    public UserTransaction update(UserTransaction userTransaction) {
        Optional<UserTransaction> existingTransaction = userTransactionRepository.findById(userTransaction.getTransactionId());
        if (existingTransaction.isPresent()) {
            return userTransactionRepository.save(userTransaction);
        } else {
            return null;
        }
    }

    @Override
    public List<UserTransaction> getAll() {
        return userTransactionRepository.findAll();
    }

    @Override
    public UserTransaction getById(Integer id) {
        return userTransactionRepository.findById(id).orElse(null);
    }

    @Override
    public UserTransaction getByCreateAt(LocalDateTime creatAt) {
        return userTransactionRepository.findUserTransactionByCreateAt(creatAt);
    }

    @Override
    public double calculateMoneyByDistance(double distance) {
        return distance*10000;
    }
}
