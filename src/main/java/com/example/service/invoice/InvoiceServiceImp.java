package com.example.service.invoice;

import com.example.entity.Invoice;
import com.example.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImp implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;


    @Override
    public Invoice add(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public void delete(@RequestBody Invoice invoice) {
        invoiceRepository.delete(invoice);
    }

    @Override
    public Invoice update(Invoice invoice) {
        Optional<Invoice> existingInvoice = invoiceRepository.findById(invoice.getInvoiceId());
        if (existingInvoice.isPresent()) {
            return invoiceRepository.save(invoice);
        } else {
            return null;
        }
    }

    @Override
    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getById(Integer id) {
        return invoiceRepository.findById(id).orElse(null);
    }
}
