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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

    @PostMapping("/customer/coupon/get")
    public Coupon getCouponByCustomer(@RequestBody Coupon coupon){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        List<Coupon> customerCoupons = couponService.getCouponByCustomerId(account.getAccountId());
        for (Coupon customerCoupon: customerCoupons){
            if(customerCoupon.getCouponName().equals(coupon.getCouponName())){
                return null;
            }
        }
        if(coupon.getCouponQuantity()==1){
            coupon.setCustomer(customerService.findCustomerById(account.getAccountId()));
            return couponService.addCoupon(coupon);
        }
        Coupon newCoupon = new Coupon();
        newCoupon.setCouponName(coupon.getCouponName());
        newCoupon.setCouponQuantity(1);
        newCoupon.setCouponValue(coupon.getCouponValue());
        newCoupon.setCustomer(customerService.findCustomerById(account.getAccountId()));
        newCoupon.setCouponType(coupon.getCouponType());
        coupon.setCouponQuantity(coupon.getCouponQuantity()-1);
        couponService.addCoupon(coupon);
        return couponService.addCoupon(newCoupon);
    }

    @GetMapping("/customer/coupon/myCoupon")
    public List<Coupon> myCoupon(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        return couponService.getCouponByCustomerId(account.getAccountId());
    }

    @GetMapping("/customer/coupon/trade-history")
    public List<Coupon> myTradeCoupon(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        return couponService.getMyTradeCoupon(account.getAccountId());
    }

    @GetMapping("/customer/point/load-point")
    public int myPoint(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account  = ourUserDetailsService.findByEmail(email);
        return customerService.findCustomerById(account.getAccountId()).getCustomerPoint();
    }

    @PostMapping("/customer/point/trade-minus")
    public Coupon tradeCoupon(@RequestBody Coupon coupon){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        Customer customer = customerService.findCustomerById(account.getAccountId());
        List<Coupon> customerCoupons = couponService.getCouponByCustomerId(account.getAccountId());
        for (Coupon customerCoupon: customerCoupons){
            if(customerCoupon.getCouponName().equals(coupon.getCouponName())){
                customerCoupon.setCouponQuantity(customerCoupon.getCouponQuantity()+1);
                customerCoupon.setTakenDate(new Date());
                coupon.setCouponQuantity(coupon.getCouponQuantity()-1);
                if(coupon.getCouponQuantity()==0){
                    couponService.deleteCoupon(coupon.getCouponId());
                } else {
                    couponService.addCoupon(coupon);
                }
                customer.setCustomerPoint(customer.getCustomerPoint()-(int) (1000 * coupon.getCouponValue()));
                return couponService.addCoupon(customerCoupon);
            }
        }

        if(coupon.getCouponQuantity()==1){
            coupon.setCustomer(customerService.findCustomerById(account.getAccountId()));
            customer.setCustomerPoint(customer.getCustomerPoint()-(int) (1000*coupon.getCouponValue()));
            coupon.setTakenDate(new Date());
            return couponService.addCoupon(coupon);
        }
        Coupon newCoupon = new Coupon();
        newCoupon.setCouponName(coupon.getCouponName());
        newCoupon.setCouponQuantity(1);
        newCoupon.setCouponValue(coupon.getCouponValue());
        newCoupon.setCustomer(customerService.findCustomerById(account.getAccountId()));
        newCoupon.setCouponType(coupon.getCouponType());
        newCoupon.setTakenDate(new Date());
        coupon.setCouponQuantity(coupon.getCouponQuantity()-1);
        couponService.addCoupon(coupon);
        customer.setCustomerPoint(customer.getCustomerPoint()-(int) (1000*coupon.getCouponValue()));
        return couponService.addCoupon(newCoupon);
    }

    @GetMapping("/customer/coupon/free-coupon-view")
    public List<Coupon> getAllFreeCoupon (){
        return couponService.getAllFreeCoupon();
    }

    @GetMapping("/customer/coupon/takenCoupon")
    public List<Coupon> getAvailableFreeCoupon (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = ourUserDetailsService.findByEmail(email);
        List<Coupon> myCoupon =  couponService.getCouponByCustomerId(account.getAccountId());
        List<Coupon> freeCoupon = couponService.getAllFreeCoupon();
        List<Coupon> result = new ArrayList<>();
        for (Coupon mine : myCoupon){
            for (Coupon free : freeCoupon){
                if(mine.getCouponName().equals(free.getCouponName())){
                    result.add(mine);
                }
            }
        }
        return result;
    }

    @GetMapping("/customer/coupon/trade-coupon-view")
    public List<Coupon> getAllTradeCoupon (){
        return couponService.getAllTradeCoupon();
    }
}