package com.example.repository;

import com.example.entity.DriverDetail;
import com.example.entity.GroupCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface GroupCarRepository extends JpaRepository<GroupCar, Integer> {
    GroupCar findByGroupName(String groupName);

    List<GroupCar> findByStartPoint(String startPoint);

    List<GroupCar> findByEndPoint(String endPoint);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO group_cars_join (customer_id, group_car_id) VALUES (:customerId, :groupCarId)", nativeQuery = true)
    void addGroupCarJoin(@Param("customerId") int customerId, @Param("groupCarId") int groupCarId);

    @Modifying
    @Transactional
    @Query(value = "SELECT  gc.*\n" +
            "from group_car gc\n" +
            "join group_cars_join gcj on gc.group_car_id = gcj.group_car_id\n" +
            "where gcj.customer_id = :customerId", nativeQuery = true)
    List<GroupCar> findGroupCarsByCustomerId(int customerId);
    GroupCar findGroupCarByCreateAt(LocalDateTime createAt);
    List<GroupCar> findGroupCarsByDriverDetail(DriverDetail driverDetail);

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM group_car WHERE driver_detail_id = :driverDetailId", nativeQuery = true)
    List<GroupCar> findGroupCarsByDriverDetailId(int driverDetailId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM group_cars_join WHERE customer_id = :customerId AND group_car_id = :groupCarId", nativeQuery = true)
    void deleteGroupCarJoin(@Param("customerId") int customerId, @Param("groupCarId") int groupCarId);


}


