package com.example.repository;

import com.example.entity.Account;
import com.example.entity.Message;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByGroupCarGroupId(Integer groupCarId);

    List<Message> findByCustomerIdAndDriverDetailId(Integer customerId, Integer driverId);
}
