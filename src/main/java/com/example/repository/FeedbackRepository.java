package com.example.repository;

import com.example.entity.Account;
import com.example.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findAllByDriverDetailId(int driverId);

}
