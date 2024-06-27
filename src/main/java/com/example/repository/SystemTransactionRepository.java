package com.example.repository;

import com.example.entity.SystemTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemTransactionRepository extends JpaRepository<SystemTransaction, String> {
    List<SystemTransaction> findAllByAccount_Email(String email);
}
