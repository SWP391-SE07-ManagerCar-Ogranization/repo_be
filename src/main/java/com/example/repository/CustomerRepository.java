package com.example.repository;

import com.example.entity.Customer;
import com.example.entity.GroupCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Repository

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO group_cars_join (customer_id, group_car_id) VALUES (:customerId, :groupCarId)", nativeQuery = true)
    void GroupCarJoin(@Param("customerId") int customerId, @Param("groupCarId") int groupCarId);


    @Query(value = "SELECT customer_id FROM group_cars_join WHERE group_car_id = :groupCarId", nativeQuery = true)
    List<Integer> getAllCustomerByGroupCarId(@Param("groupCarId") Integer groupCarId);
}
