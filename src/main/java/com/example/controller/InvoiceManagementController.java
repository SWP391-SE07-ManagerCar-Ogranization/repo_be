package com.example.controller;

import com.example.dto.TranInvoResReq;
import com.example.entity.*;
import com.example.repository.CustomerRepository;
import com.example.service.DriverDetail.DriverDetailService;
import com.example.service.DriverType.DriverTypeService;
import com.example.service.Transaction.TransactionService;
import com.example.service.customer.CustomerService;
import com.example.service.invoice.InvoiceService;
import com.example.service.payment.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/public")
public class InvoiceManagementController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private PaymentMethodService paymentMethodService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DriverTypeService driverTypeService;
    @Autowired
    private DriverDetailService driverDetailService;

    @GetMapping("/")
    public String test() {
        return "test anh 1 quan 1234578";
    }

    @PostMapping("/add")
    public ResponseEntity<Invoice> addInvoice(@RequestBody Invoice invoice) {
//        Transaction transaction = new Transaction(invoice.getBookingDate() ,
//                1000 , invoice.getCustomer() , invoice.getDriverDetail() ,
//                invoice.);

        return ResponseEntity.ok(invoiceService.add(invoice));
    }

    @PutMapping("/update")
    public ResponseEntity<Invoice> updateInvoice(@RequestBody Invoice invoice) {
        Invoice updatedInvoice = invoiceService.update(invoice);
        if (updatedInvoice != null) {
            return ResponseEntity.ok(updatedInvoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Integer id) {
        Invoice invoice = invoiceService.getById(id);
        if (invoice != null) {
            invoiceService.delete(invoice);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Integer id) {
        Invoice invoice = invoiceService.getById(id);
        if (invoice != null) {
            return ResponseEntity.ok(invoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAll());
    }

    @GetMapping("/listDriverType")
    public ResponseEntity<List<DriverType>> getAllDriverType() {
        return ResponseEntity.ok(driverTypeService.getAll());
    }

    @PostMapping("addtran1/invoi")
    public ResponseEntity<Invoice> addtranInvoice(@RequestBody Invoice invoice, @RequestParam Integer paymentMethodId) {
        Transaction transaction = new Transaction(invoice.getBookingDate(), 1000, invoice.getCustomer(), invoice.getDriverDetail(), paymentMethodService.getById(paymentMethodId));
        int invoiceId = transactionService.add(transaction).getTransactionId();
        return ResponseEntity.ok(invoice);
    }

    @PostMapping("addtran/invoice")
    public ResponseEntity<List<DriverDetail>> addTranInvoice(@RequestBody TranInvoResReq resrep) {
// resrep.get
        Transaction transaction = new Transaction(new Date(), resrep.getAmount()
                , customerService.findCustomerById(resrep.getAccount().getAccountId()), resrep.getDriverDetail(),
                resrep.getPaymentMethod());
        Transaction newTran = transactionService.add(transaction);
        Invoice newInvoice = new Invoice(new Date(), resrep.getStartPoint(),
                resrep.getEndPoint(), resrep.isFinish(), resrep.getTimeStart(),
                customerService.findCustomerById(resrep.getAccount().getAccountId()),
                resrep.getDriverDetail(), newTran);
        List<DriverDetail> listDriverDetail = driverDetailService.findAllByDriverTypeId(resrep.getDriverType().getDriverTypeId());

        try {
            invoiceService.add(newInvoice);
            return ResponseEntity.ok(listDriverDetail);
//               return  ResponseEntity.ok(newInvoice);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }






}
