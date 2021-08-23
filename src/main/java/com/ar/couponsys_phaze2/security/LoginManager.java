package com.ar.couponsys_phaze2.security;

import com.ar.couponsys_phaze2.serveces.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
//@Transactional
//@RequiredArgsConstructor

@Data
public class LoginManager {

    @Autowired
    private ApplicationContext ctx;

    public ClientService login(String email, String password, ClientType clientType){
        ClientService clientService = null;
        switch (clientType) {
            case Administrator:
                AdminService adminServiceImpl = ctx.getBean(AdminService.class);
                if (((ClientService)adminServiceImpl).login(email, password)) {
                    return (ClientService)adminServiceImpl;
            }
            break;
            case Company:
                CompanyService companyService = ctx.getBean(CompanyService.class);
                if ( ((ClientService)companyService).login(email,password)){
                    ((CompanyServiceImpl) companyService).setCompanyID(companyService.getCompanyIdByEmailPassword(email, password));
                    return  (CompanyServiceImpl)companyService;
                }

            case Customer:
                CustomerService customerService = ctx.getBean(CustomerService.class);
                if ( ((ClientService)customerService).login(email,password)){
                    ((CustomerServiecImpl) customerService).setCustomerID(customerService.getCustomerIdByEmailPassword(email, password));
                    return  (CustomerServiecImpl)customerService;
                }
        }

        return null;
    }

}
