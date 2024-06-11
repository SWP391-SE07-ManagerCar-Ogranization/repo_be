package com.example.repository;

import com.example.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionStatisticRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "SELECT * FROM transaction WHERE DATE(create_at) = DATE(:date)", nativeQuery = true)
    List<Transaction> getTransactionByDate(@Param("date") Date date);


    @Query(value = "SELECT * FROM transaction WHERE YEAR(create_at) = YEAR(:date) AND MONTH(create_at) = MONTH(:date)", nativeQuery = true)
    List<Transaction> getTransactionByMonth(@Param("date") Date date);


    @Query(value = "SELECT * FROM transaction WHERE YEAR(create_at) = YEAR(:date)", nativeQuery = true)
    List<Transaction> getTransactionByYear(@Param("date") Date date);

}
