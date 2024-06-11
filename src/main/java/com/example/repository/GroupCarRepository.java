package com.example.repository;

import com.example.entity.GroupCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GroupCarRepository extends JpaRepository<GroupCar, Integer> {
    GroupCar findByGroupName(String groupName);

    List<GroupCar> findByStartPoint(String startPoint);

    List<GroupCar> findByEndPoint(String endPoint);
}


