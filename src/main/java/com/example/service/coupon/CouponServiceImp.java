package com.example.service.coupon;

import com.example.entity.Coupon;
import com.example.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImp implements CouponService{
    @Autowired
    CouponRepository couponRepository;

    @Override
    public List<Coupon> getAllCoupon() {
        List<Coupon> availableCoupon = new ArrayList<>();
        List<Coupon> allCoupons = couponRepository.findAll();
        for (Coupon coupon : allCoupons) {
            if (coupon.getCustomer() == null) {
                availableCoupon.add(coupon);
            }
        }
        return availableCoupon;
    }

    @Override
    public Coupon addCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public void deleteCoupon(int couponId) {
        couponRepository.deleteById(couponId);
    }

    @Override
    public Coupon findCouponById(int couponId) {
        return couponRepository.findById(couponId).orElse(null);
    }

    @Override
    public List<Coupon> getCouponByCustomerId(int customerId) {
        List<Coupon> myCoupon = new ArrayList<>();
        List<Coupon> allCoupons = couponRepository.findAll();
        for (Coupon coupon : allCoupons) {
            if (coupon.getCustomer() != null){
                if (coupon.getCustomer().getId() == customerId) {
                    myCoupon.add(coupon);
                }
            }
        }
        return myCoupon;
    }
}
