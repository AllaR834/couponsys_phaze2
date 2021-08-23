package com.ar.couponsys_phaze2.serveces;

import com.ar.couponsys_phaze2.beans.Company;
import com.ar.couponsys_phaze2.beans.Customer;
import com.ar.couponsys_phaze2.exceptions.CouponSystemException;
import com.ar.couponsys_phaze2.exceptions.ErrMsg;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdminServiceImpl extends ClientService implements AdminService{


    @Override
    public void addCompany(Company company) throws CouponSystemException {

        if (companyRepository.existsByName(company.getName()))
            throw new CouponSystemException(ErrMsg.COMPANY_NAME_EXISTS);
        if (companyRepository.existsByEmail(company.getEmail()))
            throw new CouponSystemException(ErrMsg.COMPANY_EMAIL_EXISTS);

        this.companyRepository.save(company);
    }

    @Override
    public void updateCompany(int companyId, Company company) throws CouponSystemException {
        if (!companyRepository.existsById(companyId))
            throw new CouponSystemException(ErrMsg.COMPANY_NOT_EXISTS);
        else {
            Company toUpdate = this.companyRepository.getById(companyId);
            if (!toUpdate.getName().equals(company.getName()))
                throw  new CouponSystemException(ErrMsg.COMPANY_NAME_NOT_EDITABLE);
            if (companyRepository.existsByEmailAndIdNot(company.getEmail(), companyId)) {
                throw  new CouponSystemException(ErrMsg.COMPANY_EMAIL_EXISTS);
            }

            companyRepository.saveAndFlush(company);
        }
    }

    @Transactional
    @Override
    public void deleteCompany(int companyId) throws CouponSystemException {
        if (!companyRepository.existsById(companyId))
            throw new CouponSystemException(ErrMsg.COMPANY_NOT_EXISTS);
        else {
            companyRepository.deleteById(companyId);
            couponRepository.deletePurchasesByCompanyId(companyId);

        }
    }

    @Override
    public List<Company> getAllCompanies() {
      return companyRepository.findAll();
    }

    @Override
    public Company getSingleCompany(int companyId) throws CouponSystemException {
        Company company;
        if (!companyRepository.existsById(companyId))
            throw new CouponSystemException(ErrMsg.COMPANY_NOT_EXISTS);
        else {
            company = companyRepository.getById(companyId);
        }
        return company;
    }

    @Override
    public void addCustomer(Customer customer) throws CouponSystemException {

        if (customerRepository.existsByEmail(customer.getEmail()))
            throw new CouponSystemException(ErrMsg.CUSTOMER_EMAIL_EXISTS);

        this.customerRepository.save(customer);

    }

    @Override
    public void updateCustomer(int customerId, Customer customer) throws CouponSystemException {
        if (!customerRepository.existsById(customerId))
            throw new CouponSystemException(ErrMsg.CUSTOMER_NOT_EXISTS);
        else {
            Customer toUpdate = this.customerRepository.getById(customerId);
            if (customerRepository.existsByEmailAndIdNot(customer.getEmail(), customerId)) {
                throw  new CouponSystemException(ErrMsg.COMPANY_EMAIL_EXISTS);
            }

            customerRepository.saveAndFlush(customer);
        }
    }

    @Override
    public void deleteCustomer(int customerId) throws CouponSystemException {
        if (!customerRepository.existsById(customerId))
            throw new CouponSystemException(ErrMsg.CUSTOMER_NOT_EXISTS);
        else {
            customerRepository.deleteById(customerId);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getSingleCustomer(int customerId) throws CouponSystemException {
        Customer customer;
        if (!customerRepository.existsById(customerId))
            throw new CouponSystemException(ErrMsg.CUSTOMER_NOT_EXISTS);
        else {
            customer = customerRepository.getById(customerId);
        }
        return customer;

    }

    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }
}
