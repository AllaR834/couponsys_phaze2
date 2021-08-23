package com.ar.couponsys_phaze2.repos;

import com.ar.couponsys_phaze2.beans.Company;
import com.ar.couponsys_phaze2.beans.Coupon;
import com.ar.couponsys_phaze2.beans.Customer;
import com.ar.couponsys_phaze2.sql_query.CompanySQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByEmail(String email);
    boolean existsByName(String name);
    boolean existsByEmailAndPassword(String email, String password);
    boolean existsByEmailAndIdNot(String email, int id);

    Company findByName(String name);
    Company findById(int id);
    Company findByEmailAndPassword(String email, String password);

    @Query(value = CompanySQL.QUERY_GET_ID_BY_TITLE_COMP, nativeQuery = true)
    int getIdByTitleAndCompany(@Param("companyId") int companyId, @Param("couponTitle") String title);

}
