package com.example.repository;

import com.example.entity.Account;
import com.example.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findAllByDriverDetailId(int driverId);

}
