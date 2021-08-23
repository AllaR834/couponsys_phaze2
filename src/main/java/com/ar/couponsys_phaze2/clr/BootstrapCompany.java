package com.ar.couponsys_phaze2.clr;

import com.ar.couponsys_phaze2.beans.Category;
import com.ar.couponsys_phaze2.beans.Company;
import com.ar.couponsys_phaze2.beans.Coupon;
import com.ar.couponsys_phaze2.beans.Customer;
import com.ar.couponsys_phaze2.repos.CompanyRepository;
import com.ar.couponsys_phaze2.repos.CouponRepository;
import com.ar.couponsys_phaze2.repos.CustomerRepository;
import com.ar.couponsys_phaze2.security.ClientType;
import com.ar.couponsys_phaze2.security.LoginManager;
import com.ar.couponsys_phaze2.serveces.AdminService;
import com.ar.couponsys_phaze2.serveces.CompanyService;
import com.ar.couponsys_phaze2.serveces.CompanyServiceImpl;
import com.ar.couponsys_phaze2.utils.Art;
import com.ar.couponsys_phaze2.utils.PrintUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
@RequiredArgsConstructor
public class BootstrapCompany implements CommandLineRunner {

    private final CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println();
        System.out.println(Art.COMPANY);

        PrintUtils.printHeader("Add Company");
        Company comp1 = Company.builder()
                .email("cola.@gmail.com")
                .name("cola")
                .password("1234")
                .build();

        Company comp2 = Company.builder()
                .email("pepsi.@gmail.com")
                .name("pepsi")
                .password("1234")
                .build();

        Company comp3 = Company.builder()
                .email("jb.@gmail.com")
                .name("jb")
                .password("1234")
                .build();

        Company comp4 = Company.builder()
                .email("aaa.@gmail.com")
                .name("aaa")
                .password("1234")
                .build();

        companyRepository.saveAll(Arrays.asList(comp1, comp2, comp3, comp4));
        companyRepository.findAll().forEach(System.out::println);


        PrintUtils.printHeader("Update Company (1)");
        comp1.setEmail("cola_updated@gmail.com");
        companyRepository.saveAndFlush(comp1);
        companyRepository.findAll().forEach(System.out::println);

        PrintUtils.printHeader("Delete Company");
        companyRepository.delete(comp4);
        companyRepository.findAll().forEach(System.out::println);

        PrintUtils.printHeader("Company findById 2");
        System.out.println(companyRepository.findById(2));

        PrintUtils.printHeader("Company find by name jb");
        System.out.println( companyRepository.findByName("jb"));

        PrintUtils.printHeader("Company find by email, password");
        System.out.println(companyRepository.findByEmailAndPassword("cola_updated@gmail.com", "1234"));
        //System.out.println(companyRepository.f("cola_updated@gmail.com", "1234"));
    }

}
