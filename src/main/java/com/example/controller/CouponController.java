package com.example.controller;

import com.example.entity.Coupon;
import com.example.service.coupon.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CouponController {
    @Autowired
    CouponService couponService;

//    @PostMapping("/admin/coupon/add")
    @PostMapping("/public/coupon/add")
    public Coupon addCoupon(@RequestBody Coupon coupon){
        return couponService.addCoupon(coupon);
    }

//    @DeleteMapping("/admin/coupon/delete/{couponId}")
    @DeleteMapping("/public/coupon/delete/{couponId}")
    public void deleteCoupon(@PathVariable int couponId){
        couponService.deleteCoupon(couponId);
    }

//    @PostMapping("/admin/coupon/edit")
    @PutMapping("/public/coupon/edit")
    public Coupon editCoupon(@RequestBody Coupon coupon){
        return couponService.addCoupon(coupon);
    }

//    @GetMapping("/adminuser/coupon/view")
    @GetMapping("/public/coupon/view")
    public List<Coupon> getAllCoupon (){
        return couponService.getAllCoupon();
    }
}
