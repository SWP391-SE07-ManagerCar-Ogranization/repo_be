package com.example.controller;

import com.example.dto.TranInvoResReq;
import com.example.entity.*;
import com.example.service.DriverDetail.DriverDetailService;
import com.example.service.DriverType.DriverTypeService;
import com.example.service.account.OurUserDetailsService;
import com.example.service.position.PositionService;
import com.example.service.transaction.UserTransactionService;
import com.example.service.customer.CustomerService;
import com.example.service.invoice.InvoiceService;
import com.example.service.payment.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/public/invoice")
public class InvoiceManagementController {
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private UserTransactionService userTransactionService;
    @Autowired
    private PaymentMethodService paymentMethodService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DriverTypeService driverTypeService;
    @Autowired
    private DriverDetailService driverDetailService;
    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    @Autowired
    private PositionService positionService;

    @PostMapping("/add/new-trip")
    public ResponseEntity<?> addInvoice(@RequestBody Invoice invoice) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        LocalDateTime bookingDate = LocalDateTime.now();
        Account accountCustomer = ourUserDetailsService.findByEmail(email);
        Account accountDriver = positionService.calculateNearestDriver(accountCustomer);
        double distance = positionService.calculateDistanceByName(invoice.getStartPoint(), invoice.getEndPoint());
        invoice.setBookingDate(bookingDate);
        invoice.setCustomer(accountCustomer.getCustomer());
        invoice.setDriverDetail(accountDriver.getDriverDetail());

        UserTransaction transaction = new UserTransaction();
        transaction.setPaymentMethod(paymentMethodService.getById(2));
        transaction.setAmount(userTransactionService.calculateMoneyByDistance(distance));
        transaction.setCustomer(accountCustomer.getCustomer());
        transaction.setDriverDetail(accountDriver.getDriverDetail());
        transaction.setCreateAt(bookingDate);
        userTransactionService.add(transaction);
        UserTransaction transactionUpdated = userTransactionService.getByCreateAt(bookingDate);
        invoice.setUserTransaction(transactionUpdated);
        invoice.setInvoiceId(transactionUpdated.getTransactionId());
        invoiceService.add(invoice);
        return ResponseEntity.ok(transactionUpdated);
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

    @PostMapping("/addtran1/invoi")
    public ResponseEntity<Invoice> addtranInvoice(@RequestBody Invoice invoice, @RequestParam Integer paymentMethodId) {
//        UserTransaction userTransaction = new UserTransaction(invoice.getBookingDate(), 1000, invoice.getCustomer(), invoice.getDriverDetail(), paymentMethodService.getById(paymentMethodId));
//        int invoiceId = userTransactionService.add(userTransaction).getTransactionId();
        return ResponseEntity.ok(invoice);
    }

    @PostMapping("/addtran/invoice")
    public ResponseEntity<List<DriverDetail>> addTranInvoice(@RequestBody TranInvoResReq resrep) {
// resrep.get
//        UserTransaction userTransaction = new UserTransaction(new Date(), resrep.getAmount()
//                , customerService.findCustomerById(resrep.getAccount().getAccountId()), resrep.getDriverDetail(),
//                resrep.getPaymentMethod());
//        UserTransaction newTran = userTransactionService.add(userTransaction);
//        Invoice newInvoice = new Invoice(new Date(), resrep.getStartPoint(),
//                resrep.getEndPoint(), resrep.isFinish(), resrep.getTimeStart(),
//                customerService.findCustomerById(resrep.getAccount().getAccountId()),
//                resrep.getDriverDetail(), newTran);
//        List<DriverDetail> listDriverDetail = driverDetailService.findAllByDriverTypeId(resrep.getDriverType().getDriverTypeId());
//
//        try {
//            invoiceService.add(newInvoice);
//            return ResponseEntity.ok(listDriverDetail);
////               return  ResponseEntity.ok(newInvoice);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
        return null;
    }
}





