package com.example.controller;

import com.example.dto.InfoBookingForDriver;
import com.example.dto.ReqRes;
import com.example.entity.*;
import com.example.service.DriverType.DriverTypeService;
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
import java.util.Objects;

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
    private DriverTypeService driverTypeService;

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
            InfoBookingForDriver info = new InfoBookingForDriver();
            info.setInvoice(invoice);
            info.setUserTransaction(invoice.getUserTransaction());
            info.setNameCustomer(invoice.getCustomer().getAccount().getName());
            infoBookingForDrivers.add(info);
        }
        return ResponseEntity.ok(infoBookingForDrivers);
    }
    @PostMapping("/confirm-invoice")
    public ResponseEntity<?> confirmInvoice(@RequestBody Invoice invoiceInput) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        DriverDetail driverDetail = account.getDriverDetail();
        Invoice invoice = invoiceService.getById(invoiceInput.getInvoiceId());
        if(Objects.equals(invoice.getDriverDetail(), driverDetail)) {
            UserTransaction userTransaction = invoice.getUserTransaction();
            if (userTransaction.isTransactionStatus()) {
                invoice.setFinish(true);
                invoiceService.add(invoice);
                return ResponseEntity.ok("Update Trip Finished");
            } else {
                return ResponseEntity.ok("User need to payment");
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/list/driver-type/car")
    public ResponseEntity<?> getAllDriverType() {
        return ResponseEntity.ok(driverTypeService.getAllDriverCar());
    }
}
