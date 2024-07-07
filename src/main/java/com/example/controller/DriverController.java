package com.example.controller;

import com.example.dto.InfoBookingForDriver;
import com.example.dto.ReqRes;
import com.example.entity.*;
import com.example.service.DriverType.DriverTypeService;
import com.example.service.account.OurUserDetailsService;
import com.example.service.DriverDetail.DriverDetailService;
import com.example.service.groupcar.GroupCarService;
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
    @Autowired
    private GroupCarService groupCarService;
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
    @PostMapping("/join-group")
    public ResponseEntity<?> joinGroup(@RequestBody GroupCar groupCar) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        DriverDetail driverDetail = account.getDriverDetail();
        GroupCar groupCarNew = groupCarService.getGroupCarById(groupCar.getGroupId());
        groupCarNew.setDriverDetail(driverDetail);
        groupCarService.saveGroupCar(groupCarNew);
        System.out.println("group id: "+ groupCarService.getGroupCarById(groupCar.getGroupId()));
        userTransactionService.addTransactionWithGroupCar(groupCarService.getGroupCarById(groupCar.getGroupId()));
        return ResponseEntity.ok(groupCarNew);
    }
    @GetMapping("/list/group-had-join/{id}")
    public ResponseEntity<?> getAllDriverByGroupCar(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        GroupCar groupCarNew = groupCarService.getGroupCarById(id);
        DriverDetail driverDetail = groupCarNew.getDriverDetail();
        if(driverDetail ==null) {
            return ResponseEntity.badRequest().build();
        } else {
            Account accountDriver = driverDetail.getAccount();
            return ResponseEntity.ok(accountDriver);
        }
    }
    @GetMapping("/list/driver-type/car")
    public ResponseEntity<?> getAllDriverType() {
        return ResponseEntity.ok(driverTypeService.getAllDriverCar());
    }
}
