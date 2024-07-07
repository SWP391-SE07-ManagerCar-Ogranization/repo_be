package com.example.repository;

import com.example.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice , Integer> {

        @Query(value = "SELECT i.* FROM invoice i JOIN driver d ON i.driver_detail_id = d.driver_detail_id WHERE d.driver_detail_id = :driverDetailId AND d.driver_type_id = :driverTypeId", nativeQuery = true)
        List<Invoice> findAllByDriverDetailIdAndDriverTypeId(@Param("driverDetailId") Integer driverDetailId, @Param("driverTypeId") Integer driverTypeId);

        @Query(value = "SELECT * FROM invoice i JOIN driver d ON i.driver_detail_id = d.driver_detail_id WHERE d.driver_type_id = :driverTypeId AND i.customer_id = :customerId", nativeQuery = true)
        List<Invoice> findAllByDriverTypeIdAndCustomerId( @Param("customerId") Integer customerId,@Param("driverTypeId") Integer driverTypeId);



}
