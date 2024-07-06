package com.example.repository;

import com.example.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Integer> {
    UserTransaction findUserTransactionByCreateAt(LocalDateTime createAt);
}
