package com.example.repository;

import com.example.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO group_cars_join (customer_id, group_car_id) VALUES (:customerId, :groupCarId)", nativeQuery = true)
    void GroupCarJoin(@Param("customerId") int customerId, @Param("groupCarId") int groupCarId);
}
