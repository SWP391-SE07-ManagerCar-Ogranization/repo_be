package com.example.repository;

import com.example.entity.Customer;
import com.example.entity.GroupCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO group_cars_join (customer_id, group_car_id) VALUES (:customerId, :groupCarId)", nativeQuery = true)
    void GroupCarJoin(@Param("customerId") int customerId, @Param("groupCarId") int groupCarId);

    List<Customer> findCustomersByGroupCars(GroupCar groupCar);

    @Modifying
    @Transactional
    @Query(value = "select a.account_id,\n" +
            "\t   a.name,\n" +
            "       a.phone\n" +
            "from account a \n" +
            "join group_cars_join gcj on a.account_id = gcj.customer_id\n" +
            "join group_car gc on gc.group_car_id = gcj.group_car_id\n" +
            "where gc.group_car_id = :groupId", nativeQuery = true)
    List<Customer> getCustomersByGroupId(@Param("groupId") Integer groupId);
}
