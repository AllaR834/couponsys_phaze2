package com.ar.couponsys_phaze2.serveces;

import com.ar.couponsys_phaze2.exceptions.ErrMsg;
import com.ar.couponsys_phaze2.repos.CompanyRepository;
import com.ar.couponsys_phaze2.repos.CouponRepository;
import com.ar.couponsys_phaze2.repos.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClientService {
    @Autowired
    protected CouponRepository couponRepository;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected CompanyRepository companyRepository;

    public abstract boolean login(String email, String password);


}
