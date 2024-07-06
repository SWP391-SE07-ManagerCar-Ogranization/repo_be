package com.example.service.coupon;

import com.example.entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    List<Coupon> getAllCoupon ();
    List<Coupon> getAllFreeCoupon();
    List<Coupon> getAllTradeCoupon();
    Coupon addCoupon (Coupon coupon);
    void deleteCoupon(int couponId);
    Coupon findCouponById(int couponId);
    List<Coupon> getCouponByCustomerId(int customerId);
}
