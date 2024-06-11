package com.example.repository;


import com.example.entity.DriverType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverTypeRepository  extends JpaRepository<DriverType, Integer> {
}
