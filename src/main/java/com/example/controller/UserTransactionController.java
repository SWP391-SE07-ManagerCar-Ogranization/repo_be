package com.example.controller;

import com.example.dto.BillingDTO;
import com.example.dto.InfoBookingForDriver;
import com.example.entity.*;
import com.example.service.account.OurUserDetailsService;
import com.example.service.coupon.CouponService;
import com.example.service.groupcar.GroupCarService;
import com.example.service.transaction.UserTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/public/transaction")
public class UserTransactionController {
    @Autowired
    private UserTransactionService userTransactionService;
    @Autowired
    private OurUserDetailsService ourUserDetailsService;
    @Autowired
    private GroupCarService groupCarService;
    @Autowired
    private CouponService couponService;
    @PostMapping("/payment")
    public ResponseEntity<?> paymentForDriver(@RequestBody BillingDTO billingDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserTransaction transactionGet = userTransactionService.getById(billingDTO.getUserTransaction().getTransactionId());
        Coupon coupon = billingDTO.getCoupon();
        Account accountCustomer = ourUserDetailsService.findByEmail(email);
        double walletCustomer = accountCustomer.getAccountBalance();
        DriverDetail driverDetail = transactionGet.getDriverDetail();
        Account accountDriver = driverDetail.getAccount();
        double walletDriver = accountDriver.getAccountBalance();
        double totalAmountOrigin = billingDTO.getUserTransaction().getAmount();
        double totalAmountDiscount = 0.0;
        if (coupon != null) {
            totalAmountDiscount = totalAmountOrigin * (1 - coupon.getCouponValue());
        }
        if (walletCustomer > totalAmountOrigin) {
            if (!transactionGet.isTransactionStatus()) {
                if(totalAmountDiscount > 0) {
                    transactionGet.setAmount(totalAmountDiscount);
                    accountCustomer.setAccountBalance(walletCustomer - totalAmountDiscount);
                    couponService.deleteCoupon(coupon.getCouponId());
                } else {
                    accountCustomer.setAccountBalance(walletCustomer - totalAmountOrigin);
                }
                accountDriver.setAccountBalance(walletDriver + totalAmountOrigin);
                transactionGet.setTransactionStatus(true);
                userTransactionService.add(transactionGet);
                ourUserDetailsService.addAccount(accountCustomer);
                ourUserDetailsService.addAccount(accountDriver);
                return ResponseEntity.ok("Transaction Successfully");
            } else {
                return ResponseEntity.ok("Transaction had payment");
            }
        }
        return ResponseEntity.status(403).body("Not enough balance to payment");
    }
    @GetMapping("/get/group-car/{id}")
    public ResponseEntity<?> getTransactionByGroupCar(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account accountCustomer = ourUserDetailsService.findByEmail(email);
        Customer customer = accountCustomer.getCustomer();
        UserTransaction transaction = userTransactionService.findByCustomerAndGroup(customer, groupCarService.getGroupCarById(id));
        InfoBookingForDriver infoBookingForDriver = new InfoBookingForDriver();
        infoBookingForDriver.setUserTransaction(transaction);
        infoBookingForDriver.setAccountDriver(transaction.getDriverDetail().getAccount());
        return ResponseEntity.ok(infoBookingForDriver);
    }

}
