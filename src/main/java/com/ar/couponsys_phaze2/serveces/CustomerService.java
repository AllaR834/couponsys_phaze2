package com.ar.couponsys_phaze2.serveces;

import com.ar.couponsys_phaze2.beans.Category;
import com.ar.couponsys_phaze2.beans.Coupon;
import com.ar.couponsys_phaze2.beans.Customer;
import com.ar.couponsys_phaze2.exceptions.CouponSystemException;

import java.sql.SQLException;
import java.util.List;

public interface CustomerService {
    int getCustomerIdByEmailPassword(String email, String password);
    Customer getCustomerDetails();
    void addCouponPurchase(Coupon coupon) throws CouponSystemException;
    boolean existsCouponPurchase(Coupon coupon);

    List<Coupon> getCustomerPurchases();
    List<Coupon> getCustomerPurchases(Category category);
    List<Coupon> getCustomerPurchases(double maxPrice);

}
