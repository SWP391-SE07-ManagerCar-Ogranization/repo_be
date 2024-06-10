package com.example.service.coupon;

import com.example.entity.Coupon;
import com.example.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImp implements CouponService{
    @Autowired
    CouponRepository couponRepository;

    @Override
    public List<Coupon> getAllCoupon() {
        return couponRepository.findAll();
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
    public Optional<Coupon> findCouponById(int couponId) {
        return couponRepository.findById(couponId);
    }
}
