package com.example.controller;

import com.example.dto.InfoBookingForDriver;
import com.example.entity.*;
import com.example.service.account.OurUserDetailsService;
import com.example.service.position.PositionService;
import com.example.service.transaction.UserTransactionService;
import com.example.service.invoice.InvoiceService;
import com.example.service.payment.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
        if(accountDriver.getDriverDetail() == null) {
            return ResponseEntity.badRequest().build();
        } else {
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
            return ResponseEntity.ok(new InfoBookingForDriver(invoice,null,transactionUpdated,null,accountCustomer.getName(), accountDriver, 1L));
        }
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
    @GetMapping("/customer/invoices")
    public ResponseEntity<?> getInvoiceByCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account accountCustomer = ourUserDetailsService.findByEmail(email);
        return ResponseEntity.ok(invoiceService.findByCustomer(accountCustomer.getCustomer()));
    }

    @GetMapping("/customer/invoices/search")
    public ResponseEntity<List<Invoice>> searchInvoicesByKeyword(@RequestParam("keyword") String keyword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account accountCustomer = ourUserDetailsService.findByEmail(email);
        List<Invoice> searchResults = invoiceService.searchInvoicesByKeyword(accountCustomer.getAccountId(), keyword);
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/customer/invoices/sort")
    public ResponseEntity<List<Invoice>> getSortedInvoicesByCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account accountCustomer = ourUserDetailsService.findByEmail(email);
        List<Invoice> sortedInvoices = invoiceService.findInvoicesByCustomerSortedByTimeStart(accountCustomer.getCustomer());
        return ResponseEntity.ok(sortedInvoices);
    }
}





