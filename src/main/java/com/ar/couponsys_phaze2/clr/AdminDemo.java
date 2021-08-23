package com.ar.couponsys_phaze2.clr;

import com.ar.couponsys_phaze2.beans.Company;
import com.ar.couponsys_phaze2.beans.Customer;
import com.ar.couponsys_phaze2.exceptions.CouponSystemException;
import com.ar.couponsys_phaze2.exceptions.ErrMsg;
import com.ar.couponsys_phaze2.repos.CompanyRepository;
import com.ar.couponsys_phaze2.repos.CouponRepository;
import com.ar.couponsys_phaze2.repos.CustomerRepository;
import com.ar.couponsys_phaze2.security.ClientType;
import com.ar.couponsys_phaze2.security.LoginManager;
import com.ar.couponsys_phaze2.serveces.*;
import com.ar.couponsys_phaze2.utils.Art;
import com.ar.couponsys_phaze2.utils.PrintUtils;
import com.ar.couponsys_phaze2.utils.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Order(6)
public class AdminDemo implements CommandLineRunner {
    private CompanyService companyService;
    private AdminService adminService;
    private final LoginManager loginManager;

    @Override
    public void run(String... args) throws Exception {
        System.out.println();
        System.out.println(Art.ADMIN);

        //------------------Admin login-------------------------------
        PrintUtils.printHeader("Bad Admin login");
        adminService = (AdminService) loginManager.login("admin@admin.com","1234",ClientType.Administrator);
        PrintUtils.printLoginResult(adminService);

        PrintUtils.printHeader("Good Admin login");
        adminService = (AdminService) loginManager.login("admin@admin.com","admin",ClientType.Administrator);
        PrintUtils.printLoginResult(adminService);

        //------------------Get all companies-------------------------------
        PrintUtils.printHeader("Get all companies");
        adminService.getAllCompanies().forEach(System.out::println);

        //------------------Get single company-------------------------------
        PrintUtils.printHeader("Get single company, id = 2");
        System.out.println(adminService.getSingleCompany(2));
        //------------------Add company-------------------------------
        PrintUtils.printHeader("Add Company");
        Company comp1 = Company.builder()
                .email("ksp.@gmail.com")
                .name("ksp")
                .password("1234")
                .build();
        adminService.addCompany(comp1);
        adminService.getAllCompanies().forEach(System.out::println);

        PrintUtils.printHeader("Add Company with the same name");
        try {
            adminService.addCompany(comp1);
        } catch (CouponSystemException ex) {
            System.out.println(ex.getMessage());
            System.out.println();
        }

        PrintUtils.printHeader("Add Company with the same email");
        comp1.setName("ksp1");
        try {
            adminService.addCompany(comp1);
        } catch (CouponSystemException ex) {
            System.out.println(ex.getMessage());
            System.out.println();
        }
        adminService.getAllCompanies().forEach(System.out::println);

        //------------------Update company-------------------------------
        PrintUtils.printHeader("Update Company");

        Company company2 = adminService.getSingleCompany(2);
        System.out.println("Company to update:");
        System.out.println(company2);
        company2.setEmail("pepsi1234.@gmail.com");
        adminService.updateCompany(2,company2);
        System.out.println("Updated company:");
        System.out.println(adminService.getSingleCompany(2));

        //------------------Update company-------------------------------
        PrintUtils.printHeader("Try to update company(2) mail to mail exists to other company - ksp.@gmail.com ");

        company2.setEmail("ksp.@gmail.com");

        try {
            adminService.updateCompany(company2.getId(),company2);
        } catch (CouponSystemException ex) {
            System.out.println(ex.getMessage());
            System.out.println();
        }
        System.out.println("Companies:");
        adminService.getAllCompanies().forEach(System.out::println);

        //------------------Delete company-------------------------------
        PrintUtils.printHeader("Delete Company (id = 2)");
        System.out.println("Purchases list before delete company");
        adminService.getAllCustomers().forEach(this::printCustPurchases);

        adminService.deleteCompany(2);
        PrintUtils.printLine();
        System.out.println("Companies after delete :");
        PrintUtils.printLine();
        adminService.getAllCompanies().forEach(System.out::println);

        PrintUtils.printLine();
        System.out.println("Customers with purchases:");
        adminService.getAllCustomers().forEach(this::printCustPurchases);


        //------------------Get all customers-------------------------------
        PrintUtils.printHeader("Get all Customers");
        //TablePrinter.print(adminService.getAllCustomers());
        adminService.getAllCustomers().forEach(System.out::println);

        //------------------Get single customer-------------------------------
        PrintUtils.printHeader("Get single customer, id = 2");
        System.out.println(adminService.getSingleCustomer(2));

        //------------------Add customer-------------------------------
        Customer cust4 = Customer.builder()
                .email("PiterP@gmail.com")
                .password("1234")
                .firstName("Piter")
                .lasttName("Pen")
                .build();
        adminService.addCustomer(cust4);
        System.out.println("Customer to add:");
        TablePrinter.print(cust4);
        System.out.println("Companies after add:");
        adminService.getAllCustomers().forEach(System.out::println);

        PrintUtils.printHeader("Add Customer with the same email");
        try {
            adminService.addCustomer(cust4);
        } catch (CouponSystemException ex) {
            System.out.println(ex.getMessage());
            System.out.println();
        }
        adminService.getAllCustomers().forEach(System.out::println);

        //------------------Update customer-------------------------------
        PrintUtils.printHeader("Try to update customer(5) mail to mail exists to other customer - moshe2@gmail.com ");

        cust4.setEmail("moshe2@gmail.com");

        try {
            adminService.updateCustomer(cust4.getId(),cust4);
        } catch (CouponSystemException ex) {
            System.out.println(ex.getMessage());
            System.out.println();
        }
        System.out.println("Customers:");
        adminService.getAllCustomers().forEach(System.out::println);

        PrintUtils.printHeader("Update customer(5) mail and password");
        cust4.setEmail("PiterPen@gmail.com");
        cust4.setPassword("PP_1234");
        adminService.updateCustomer(cust4.getId(),cust4);
        System.out.println("Customers after update:");
        adminService.getAllCustomers().forEach(System.out::println);

        //------------------Delete Customer-------------------------------
        PrintUtils.printHeader("Delete Customer (id = 2)");
        System.out.println("Purchases list before delete customer");
        adminService.getSingleCustomer(2).getCoupons().forEach(System.out::println);

        adminService.deleteCustomer(2);
        PrintUtils.printLine();
        System.out.println("Customers after delete :");
        PrintUtils.printLine();
        adminService.getAllCustomers().forEach(System.out::println);
    }

    private void printCustPurchases(Customer customer)
    {
        PrintUtils.printLine();
        System.out.println(customer.getFirstName() + " " + customer.getLasttName());
        PrintUtils.printLine();
        System.out.println("Purchases:");
        PrintUtils.printLine();
        TablePrinter.print(customer.getCoupons());
        System.out.println();
    }


}
