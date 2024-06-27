package com.example.service.transaction;

import com.example.entity.SystemTransaction;
import com.example.entity.UserTransaction;

import java.util.List;

public interface SystemTransactionService {
    SystemTransaction add (SystemTransaction transaction);
    List<SystemTransaction> findAllByEmail(String email);
}
