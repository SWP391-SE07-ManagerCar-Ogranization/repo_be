package com.example.repository;

import com.example.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<UserTransaction, Integer> {

    @Query(value = "SELECT * FROM transaction WHERE DATE(create_at) = DATE(:date)", nativeQuery = true)
    List<UserTransaction> getTransactionByDate(@Param("date") Date date);


    @Query(value = "SELECT * FROM transaction WHERE YEAR(create_at) = YEAR(:date) AND MONTH(create_at) = MONTH(:date)", nativeQuery = true)
    List<UserTransaction> getTransactionByMonth(@Param("date") Date date);


    @Query(value = "SELECT * FROM transaction WHERE YEAR(create_at) = YEAR(:date)", nativeQuery = true)
    List<UserTransaction> getTransactionByYear(@Param("date") Date date);

}
