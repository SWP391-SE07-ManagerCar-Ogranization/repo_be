package com.example.controller;

import com.example.dto.InfoBookingForDriver;
import com.example.dto.ReqRes;
import com.example.entity.Account;
import com.example.entity.DriverDetail;
import com.example.entity.Invoice;
import com.example.entity.UserTransaction;
import com.example.service.account.OurUserDetailsService;
import com.example.service.DriverDetail.DriverDetailService;
import com.example.service.invoice.InvoiceService;
import com.example.service.transaction.UserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/public/driver")
public class DriverController {
    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    @Autowired
    private DriverDetailService driverDetailService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private UserTransactionService userTransactionService;

    @PostMapping("/update-status")
    public ResponseEntity<ReqRes> updateStatus(@RequestBody ReqRes reqRes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        DriverDetail driverDetail = account.getDriverDetail();
            driverDetail.setWorkingStatus(reqRes.isWorkingStatus());
            account.setLatitude(reqRes.getLatitude());
            account.setLongitude(reqRes.getLongitude());
            ourUserDetailsService.addAccount(account);
            driverDetailService.add(driverDetail);
            reqRes.setMessage("success");
        return ResponseEntity.ok(reqRes);
    }

    @GetMapping("/get-invoice")
    public ResponseEntity<?> getPersonalInvoice() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        DriverDetail driverDetail = account.getDriverDetail();
        List<Invoice> invoiceList = invoiceService.findByDriverDetail(driverDetail);
        List<InfoBookingForDriver> infoBookingForDrivers = new ArrayList<>();
        for (Invoice invoice: invoiceList) {
            infoBookingForDrivers.add(new InfoBookingForDriver(invoice, invoice.getUserTransaction(),invoice.getCustomer().getAccount().getName()));
        }
        return ResponseEntity.ok(infoBookingForDrivers);
    }
}
