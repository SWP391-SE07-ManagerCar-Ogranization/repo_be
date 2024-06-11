package com.example.service.coupon;

import com.example.entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponService {
    List<Coupon> getAllCoupon ();
    Coupon addCoupon (Coupon coupon);
    void deleteCoupon(int couponId);
    Optional<Coupon> findCouponById(int couponId);
}
