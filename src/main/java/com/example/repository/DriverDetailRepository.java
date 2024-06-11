package com.example.repository;

import com.example.entity.DriverDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DriverDetailRepository extends JpaRepository<DriverDetail, Integer> {
   @Query("SELECT dd FROM DriverDetail dd JOIN dd.driver d WHERE d.driverTypeId= :driverTypeId")
   List<DriverDetail> findAllByDriverTypeId(@Param("driverTypeId") Integer driverTypeId);

}


