package com.example.repository;

import com.example.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice , Integer> {
    @Query("SELECT i FROM Invoice i WHERE i.customer.account.name LIKE %:query% OR i.startPoint LIKE %:query% OR i.endPoint LIKE %:query%")
    List<Invoice> searchByQuery(@Param("query") String query);

    @Query("SELECT i FROM Invoice i " +
            "ORDER BY " +
            "CASE WHEN :sortCriteria = 'A-Z' THEN i.endPoint END ASC, " +
            "CASE WHEN :sortCriteria = 'ASC' THEN i.timeStart END ASC, " +
            "CASE WHEN :sortCriteria = 'DESC' THEN i.timeStart END DESC")
    List<Invoice> sortByCriteria(@Param("sortCriteria") String sortCriteria);


    @Query("SELECT i FROM Invoice i WHERE i.customer.id = :customerId")
    List<Invoice> findByCustomerId(@Param("customerId") Integer customerId);

}
