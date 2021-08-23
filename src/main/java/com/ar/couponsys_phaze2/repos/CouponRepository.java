package com.ar.couponsys_phaze2.repos;

import com.ar.couponsys_phaze2.beans.Category;
import com.ar.couponsys_phaze2.beans.Company;
import com.ar.couponsys_phaze2.beans.Coupon;
import com.ar.couponsys_phaze2.beans.Customer;
import com.ar.couponsys_phaze2.sql_query.CouponSQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    boolean existsById(int id);

    Coupon findById(int id);
    List<Coupon> findByCategoryAndCompanyId(Category category, int companyID);
    List<Coupon> findByPriceLessThanEqualAndCompanyId(double price, int companyID);
    List<Coupon> findByEndDateBefore(Date checkDate);

    @Query(value = CouponSQL.QUERY_FIND_COMPANY_COUPONS, nativeQuery = true)
    List<Coupon> findAllCompanyCoupons(@Param("companyId") int companyId);

    @Query(value = CouponSQL.QUERY_FIND_BY_CATEGORY_CUSTOMER, nativeQuery = true)
    List<Coupon> findByCategoryAndCustomerId(@Param("customerid") int customerId,
                                             @Param("categoryid") int categoryId);

    @Query(value = CouponSQL.QUERY_FIND_BY_CUSTOMER_PRICE, nativeQuery = true)
    List<Coupon> findByCustomerIdAndMaxPrice(@Param("customerid") int customerId,
                                             @Param("maxprice") double maxPrice);


    @Query(value = CouponSQL.QUERY_GET_ID_BY_TITLE_COMP, nativeQuery = true)
    int getIdByTitleAndCompany(@Param("companyId") int companyId,
                               @Param("couponTitle") String title);

    @Query(value = CouponSQL.QUERY_GET_PURCHASE, nativeQuery = true)
    int getPurchase(@Param("customerId") int customerId,
                    @Param("couponId") int couponId);

    @Modifying
    @Query(value = CouponSQL.QUERY_DELETE_PURCHASES, nativeQuery = true)
    void deletePurchasesByCompanyId(@Param("companyId") int companyId);

    @Modifying
    @Transactional
    @Query(value = CouponSQL.QUERY_DELETE_EXPIRED, nativeQuery = true)
    void deleteExpiredCoupons();

}
