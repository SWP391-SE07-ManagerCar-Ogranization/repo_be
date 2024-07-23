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
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://3.24.136.21")
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
        if (driverDetailService.checkValidDriver(driverDetail)) {
            driverDetail.setWorkingStatus(reqRes.isWorkingStatus());
            account.setLatitude(reqRes.getLatitude());
            account.setLongitude(reqRes.getLongitude());
            ourUserDetailsService.addAccount(account);
            driverDetailService.add(driverDetail);
            reqRes.setMessage("success");
            return ResponseEntity.ok(reqRes);
        } else {
            reqRes.setMessage("Need update information");
            return ResponseEntity.status(403).body(reqRes);
        }
    }

    @GetMapping("/get-group-car")
    public ResponseEntity<?> paymentGroupCarForDriver() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        List<GroupCar> groupCars = groupCarService.getGroupCarsByDriver(account.getDriverDetail());
        List<InfoBookingForDriver> infoBookingForDrivers = new ArrayList<>();
        for (GroupCar groupCar : groupCars) {
            InfoBookingForDriver info = new InfoBookingForDriver();
            info.setGroupCar(groupCar);
            info.setUserTransactions(groupCar.getUserTransactions());
            infoBookingForDrivers.add(info);
        }
        return ResponseEntity.ok(infoBookingForDrivers);
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<?> getDriverByInvoiceId(@PathVariable String invoiceId) {
        Invoice invoice = invoiceService.getById(Integer.valueOf(invoiceId));
        DriverDetail driverDetail = invoice.getDriverDetail();
        return ResponseEntity.ok(driverDetail.getAccount());
    }

    @GetMapping("/get-invoice")
    public ResponseEntity<?> getPersonalInvoice() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        DriverDetail driverDetail = account.getDriverDetail();
        List<Invoice> invoiceList = invoiceService.findByDriverDetail(driverDetail);

        List<InfoBookingForDriver> infoBookingForDrivers = invoiceList.stream()
                .map(invoice -> {
                    InfoBookingForDriver info = new InfoBookingForDriver();
                    info.setInvoice(invoice);
                    info.setUserTransaction(invoice.getUserTransaction());
                    info.setNameCustomer(invoice.getCustomer().getAccount().getName());
                    return info;
                })
                .sorted((info1, info2) -> info2.getInvoice().getBookingDate()
                        .compareTo(info1.getInvoice().getBookingDate()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(infoBookingForDrivers);
    }

    @PostMapping("/confirm-invoice")
    public ResponseEntity<?> confirmInvoice(@RequestBody Invoice invoiceInput) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        DriverDetail driverDetail = account.getDriverDetail();
        Invoice invoice = invoiceService.getById(invoiceInput.getInvoiceId());
        if (Objects.equals(invoice.getDriverDetail(), driverDetail)) {
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

    @PostMapping("/ignore-invoice")
    public ResponseEntity<?> ignoreInvoice(@RequestBody Invoice invoiceInput) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        DriverDetail driverDetail = account.getDriverDetail();
        Invoice invoice = invoiceService.getById(invoiceInput.getInvoiceId());
        if (Objects.equals(invoice.getDriverDetail(), driverDetail)) {
            UserTransaction userTransaction = invoice.getUserTransaction();
            userTransactionService.delete(userTransaction);
            invoiceService.delete(invoice);
            return ResponseEntity.ok("Delete finished");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/confirm-group")
    public ResponseEntity<?> confirmGroupFinished(@RequestBody GroupCar groupCarInput) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        DriverDetail driverDetail = account.getDriverDetail();
        GroupCar groupCar = groupCarService.getGroupCarById(groupCarInput.getGroupId());
        if (Objects.equals(groupCar.getDriverDetail(), driverDetail)) {
            Set<UserTransaction> userTransactions = groupCar.getUserTransactions();
            for (UserTransaction transaction : userTransactions) {
                if (transaction.isTransactionStatus()) {
                    groupCar.setFinish(true);
                } else {
                    groupCar.setFinish(false);
                }
            }
            if (groupCar.isFinish()) {
                groupCarService.saveGroupCar(groupCar);
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
        if (groupCarNew.getDriverDetail() != null) {
            return ResponseEntity.badRequest().body("GroupCar already existed driver");
        } else {
            groupCarNew.setDriverDetail(driverDetail);
            groupCarService.saveGroupCar(groupCarNew);
            userTransactionService.addTransactionWithGroupCar(groupCarService.getGroupCarById(groupCar.getGroupId()));
            return ResponseEntity.ok(groupCarNew);
        }

    }

    @GetMapping("/list/group-had-join/{id}")
    public ResponseEntity<?> getAllDriverByGroupCar(@PathVariable Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        GroupCar groupCarNew = groupCarService.getGroupCarById(id);
        DriverDetail driverDetail = groupCarNew.getDriverDetail();
        if (driverDetail == null) {
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
