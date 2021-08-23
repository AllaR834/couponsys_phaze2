package com.ar.couponsys_phaze2.serveces;

import com.ar.couponsys_phaze2.beans.Category;
import com.ar.couponsys_phaze2.beans.Company;
import com.ar.couponsys_phaze2.beans.Coupon;
import com.ar.couponsys_phaze2.beans.Customer;
import com.ar.couponsys_phaze2.exceptions.CouponSystemException;
import com.ar.couponsys_phaze2.exceptions.ErrMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerServiecImpl extends  ClientService implements CustomerService{

    private int customerID;

    @Override
    public boolean login(String email, String password) {
        return customerRepository.existsByEmailAndPassword(email, password);
    }

    @Override
    public int getCustomerIdByEmailPassword(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password).getId();
    }

    @Override
    public Customer getCustomerDetails() {
        return customerRepository.findById(customerID);
    }

    @Transactional
    @Override
    public void addCouponPurchase(Coupon coupon) throws CouponSystemException {
        if (coupon.getAmount() == 0) {
            throw new CouponSystemException(ErrMsg.COUPON_NOT_AVAILABLE);
        }
        if (coupon.getEndDate().compareTo(Date.valueOf(LocalDate.now())) < 0) {
            throw new CouponSystemException(ErrMsg.COUPON_EXPIRED);
        }
        if (existsCouponPurchase(coupon)) {
            throw new CouponSystemException(ErrMsg.PURCHASE_EXISTS);
        }

        Customer customer = customerRepository.getById(customerID);
        customer.addToCoupon(coupon);
        customerRepository.saveAndFlush(customer);
        coupon.setAmount(coupon.getAmount() - 1);
        couponRepository.saveAndFlush(coupon);
    }

    @Override
    public List<Coupon> getCustomerPurchases() {
        return customerRepository.getById(customerID).getCoupons();
    }

    @Override
    public List<Coupon> getCustomerPurchases(Category category) {
        return couponRepository.findByCategoryAndCustomerId(customerID, category.ordinal());
    }

    @Override
    public List<Coupon> getCustomerPurchases(double maxPrice) {
        return couponRepository.findByCustomerIdAndMaxPrice(customerID, 100);
    }

    @Override
    public boolean existsCouponPurchase(Coupon coupon) {
        return couponRepository.getPurchase(customerID, coupon.getId()) > 0;
    }
}
