package com.example.repository;


import com.example.entity.DriverType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverTypeRepository  extends JpaRepository<DriverType, Integer> {
}
