package com.example.repository;

import com.example.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "SELECT customer_id FROM group_cars_join WHERE group_car_id = :groupCarId", nativeQuery = true)
    List<Integer> getAllCustomerByGroupCarId(@Param("groupCarId") Integer groupCarId);
}
