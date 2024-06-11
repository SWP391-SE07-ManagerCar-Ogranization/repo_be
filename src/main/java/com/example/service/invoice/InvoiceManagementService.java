package com.example.service.invoice;

import com.example.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceManagementService {
    @Autowired
    TransactionService transactionService;
    @Autowired
    InvoiceService invoiceService;


}
