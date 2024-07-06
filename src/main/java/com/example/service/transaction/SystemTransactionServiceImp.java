package com.example.service.transaction;

import com.example.entity.SystemTransaction;
import com.example.repository.SystemTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemTransactionServiceImp implements SystemTransactionService{
    @Autowired
    SystemTransactionRepository repository;
    @Override
    public SystemTransaction add(SystemTransaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public List<SystemTransaction> findAllByEmail(String email) {
        return repository.findAllByAccount_Email(email);
    }
}
