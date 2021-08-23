package com.ar.couponsys_phaze2.serveces;

import com.ar.couponsys_phaze2.beans.Category;
import com.ar.couponsys_phaze2.beans.Company;
import com.ar.couponsys_phaze2.beans.Coupon;
import com.ar.couponsys_phaze2.exceptions.CouponSystemException;

import java.util.List;

public interface CompanyService {
    void addCoupon(Coupon coupon) throws CouponSystemException;
    void updateCoupon(int couponId, Coupon coupon) throws CouponSystemException;
    void deleteCoupon(int couponId);
    Coupon getCompanyCoupon(int couponId);

    List<Coupon> getCompanyCoupons();
    List<Coupon> getCompanyCoupons(Category category);
    List<Coupon> getCompanyCoupons(double maxPrice);

    Company getCompanyDetails();
    int getCompanyIdByEmailPassword(String email, String password);


}
