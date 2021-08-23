package com.ar.couponsys_phaze2.serveces;

import com.ar.couponsys_phaze2.beans.Category;
import com.ar.couponsys_phaze2.beans.Company;
import com.ar.couponsys_phaze2.beans.Coupon;
import com.ar.couponsys_phaze2.exceptions.CouponSystemException;
import com.ar.couponsys_phaze2.exceptions.ErrMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompanyServiceImpl extends ClientService implements CompanyService{

    private int companyID;

    @Override
    public int getCompanyIdByEmailPassword(String email, String password) {
        return companyRepository.findByEmailAndPassword(email, password).getId();
    }

    @Override
    public boolean login(String email, String password) {
        if (companyRepository.existsByEmailAndPassword(email, password)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addCoupon(Coupon coupon) throws CouponSystemException {
        if (companyRepository.getIdByTitleAndCompany( companyID,coupon.getTitle() ) != 0) {
            throw new CouponSystemException(ErrMsg.COUPON_EXISTS);
        }

        Company company = companyRepository.getById(companyID);
        company.addToCoupon(coupon);
        companyRepository.saveAndFlush(company);
    }

    @Override
    public void updateCoupon(int couponId, Coupon coupon) throws CouponSystemException {
        if (!couponRepository.existsById(couponId)) {
            throw new CouponSystemException(ErrMsg.COUPON_NOT_EXISTS);
        }
        Coupon toUpdate = couponRepository.findById(couponId);
        if (!toUpdate.getTitle().equals(coupon.getTitle())) {
            throw new CouponSystemException(ErrMsg.COUPON_TITLE_NOT_EDITABLE);
        }
        if (toUpdate.getCompany().getId() != coupon.getCompany().getId()) {
            throw new CouponSystemException(ErrMsg.COUPON_COMPANY_NOT_EDITABLE);
        }
        couponRepository.saveAndFlush(coupon);
    }

    @Override
    public void deleteCoupon(int couponId) {
        couponRepository.deleteById(couponId);
    }

    @Override
    public List<Coupon> getCompanyCoupons() {
        return companyRepository.getById(companyID).getCoupons();
    }

    @Override
    public List<Coupon> getCompanyCoupons(Category category) {
        return couponRepository.findByCategoryAndCompanyId(category, companyID);
    }

    @Override
    public Coupon getCompanyCoupon(int couponId) {
        return couponRepository.findById(couponId);
    }

    @Override
    public List<Coupon> getCompanyCoupons(double maxPrice) {
        return couponRepository.findByPriceLessThanEqualAndCompanyId(maxPrice,companyID);
    }

    @Override
    public Company getCompanyDetails() {
        if (companyRepository == null) {
            return null;
        } else {
            return this.companyRepository.findById(this.companyID);
        }
    }
}
