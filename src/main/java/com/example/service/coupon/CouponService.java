package com.example.service.coupon;

import com.example.entity.Coupon;

import java.util.List;

public interface CouponService {
    List<Coupon> getAllCoupon ();
    Coupon addCoupon (Coupon coupon);
    void deleteCoupon(int couponId);
}
