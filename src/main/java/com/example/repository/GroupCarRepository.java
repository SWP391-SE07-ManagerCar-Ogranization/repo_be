package com.example.repository;

import com.example.entity.GroupCar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupCarRepository extends JpaRepository<GroupCar, Integer> {
    GroupCar findByGroupName(String groupName);

    List<GroupCar> findByStartPoint(String startPoint);

    List<GroupCar> findByEndPoint(String endPoint);
}


