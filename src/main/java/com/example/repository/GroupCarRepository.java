package com.example.repository;

import com.example.entity.GroupCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupCarRepository extends JpaRepository<GroupCar, Integer> {
    GroupCar findByGroupName(String groupName);

    List<GroupCar> findByStartPoint(String startPoint);

    List<GroupCar> findByEndPoint(String endPoint);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO group_cars_join (customer_id, group_car_id) VALUES (:customerId, :groupCarId)", nativeQuery = true)
    void GroupCarJoin(@Param("customerId") int customerId, @Param("groupCarId") int groupCarId);

    @Modifying
    @Transactional
    @Query(value = "SELECT  gc.*\n" +
            "from group_car gc\n" +
            "join group_cars_join gcj on gc.group_car_id = gcj.group_car_id\n" +
            "where gcj.customer_id = :customerId", nativeQuery = true)
    List<GroupCar> findGroupCarsByCustomerId(int customerId);
}


