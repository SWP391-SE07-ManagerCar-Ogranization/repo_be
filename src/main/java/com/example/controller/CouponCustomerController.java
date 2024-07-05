package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Coupon;
import com.example.entity.Customer;
import com.example.service.account.OurUserDetailsService;
import com.example.service.coupon.CouponService;
import com.example.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/public")
public class CouponCustomerController {
    @Autowired
    CouponService couponService;
    @Autowired
    CustomerService customerService;
    @Autowired
    OurUserDetailsService ourUserDetailsService;

    @PostMapping("/customer/coupon/get/{couponId}")
    public Coupon getCouponByCustomer(@PathVariable int couponId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account  = ourUserDetailsService.findByEmail(email);
        Coupon coupon = couponService.findCouponById(couponId);
        coupon.setCustomer(customerService.findCustomerById(account.getAccountId()));
        return couponService.addCoupon(coupon);
    }

    @GetMapping("/customer/coupon/myCoupon")
    public List<Coupon> myCoupon(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account  = ourUserDetailsService.findByEmail(email);
        return couponService.getCouponByCustomerId(account.getAccountId());
    }

    @GetMapping("/customer/point/load-point")
    public int myPoint(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account  = ourUserDetailsService.findByEmail(email);
        return customerService.getCustomer(account.getAccountId()).getCustomerPoint();
    }

    @PostMapping("/customer/point/trade-minus")
    public Coupon tradeCoupon(@RequestBody Coupon coupon){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        Customer customer = customerService.findCustomerById(account.getAccountId());
        coupon.setCustomer(customer);
        if (coupon.getCouponValue() == 0.1) {
            customer.setCustomerPoint(customer.getCustomerPoint()-100);
        } else if (coupon.getCouponValue() == 0.2) {
            customer.setCustomerPoint(customer.getCustomerPoint()-200);
        } else if (coupon.getCouponValue() == 0.25) {
            customer.setCustomerPoint(customer.getCustomerPoint()-300);
        }
        return couponService.addCoupon(coupon);
    }
}
