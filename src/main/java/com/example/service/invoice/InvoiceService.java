package com.example.service.invoice;

import com.example.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    Invoice add(Invoice invoice);

    void delete(Invoice invoice);

    Invoice update(Invoice invoice);

    List<Invoice> getAll();

    Invoice getById(Integer id);

}
