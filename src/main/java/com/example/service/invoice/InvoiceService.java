package com.example.service.invoice;

import com.example.dto.TranInvoResReq;
import com.example.entity.Invoice;
import com.example.entity.Transaction;

import java.util.List;

public interface InvoiceService {
    Invoice add(Invoice invoice);

    void delete(Invoice invoice);

    Invoice update(Invoice invoice);

    List<Invoice> getAll();

    Invoice getById(Integer id);

   List<Invoice> findInvoiceByQuery(String query);

    List<Invoice> sortInvoices(String sortCriteria);
    ///
    List<Invoice> getInvoicesByCustomerId(Integer customerId);

}
