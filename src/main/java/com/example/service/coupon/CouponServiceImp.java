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
    public List<Coupon> getAllFreeCoupon() {
        List<Coupon> coupons = couponRepository.findAll();
        List<Coupon> result = new ArrayList<>();
        for (Coupon coupon : coupons){
            if((coupon.getCouponType().equals("Free coupon"))&&(coupon.getCustomer()==null)&&(coupon.getCouponQuantity()>0)){
                result.add(coupon);
            }
        }
        return result;
    }

    @Override
    public List<Coupon> getAllTradeCoupon() {
        List<Coupon> coupons = couponRepository.findAll();
        List<Coupon> result = new ArrayList<>();
        for (Coupon coupon : coupons){
            if(coupon.getCouponType().equals("Trade coupon")&&(coupon.getCustomer()==null)&&(coupon.getCouponQuantity()>0)){
                result.add(coupon);
            }
        }
        return result;
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