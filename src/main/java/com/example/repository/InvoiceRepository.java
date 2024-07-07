package com.example.repository;

import com.example.entity.DriverDetail;
import com.example.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice , Integer> {
    List<Invoice> findAllByDriverDetail(DriverDetail driverDetail);
}
