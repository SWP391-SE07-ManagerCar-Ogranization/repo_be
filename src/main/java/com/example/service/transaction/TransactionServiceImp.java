package com.example.service.transaction;


import com.example.entity.Transaction;
import com.example.repository.TransactionRepository;
import com.example.repository.GroupCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImp implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction add(Transaction transaction) {
        return transactionRepository.save(transaction);
    }


    @Override
    public void delete(@RequestBody Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    @Override
    public Transaction update(Transaction transaction) {
        Optional<Transaction> existingTransaction = transactionRepository.findById(transaction.getTransactionId());
        if (existingTransaction.isPresent()) {
            return transactionRepository.save(transaction);
        } else {
            return null;
        }
    }

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getById(Integer id) {
        return transactionRepository.findById(id).orElse(null);
    }




}
