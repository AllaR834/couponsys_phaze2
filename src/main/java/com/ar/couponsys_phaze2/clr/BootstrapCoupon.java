package com.ar.couponsys_phaze2.clr;

import com.ar.couponsys_phaze2.beans.Category;
import com.ar.couponsys_phaze2.beans.Company;
import com.ar.couponsys_phaze2.beans.Coupon;
import com.ar.couponsys_phaze2.beans.Customer;
import com.ar.couponsys_phaze2.repos.CompanyRepository;
import com.ar.couponsys_phaze2.repos.CouponRepository;
import com.ar.couponsys_phaze2.repos.CustomerRepository;
import com.ar.couponsys_phaze2.utils.Art;
import com.ar.couponsys_phaze2.utils.PrintUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@Order(3)
@RequiredArgsConstructor
public class BootstrapCoupon implements CommandLineRunner {

    private final CouponRepository couponRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println();
        System.out.println(Art.COUPON);

        Company comp1 = companyRepository.findById(1);

        PrintUtils.printHeader("Add Coupon");
        Coupon coupon1 = Coupon.builder()
                .company(comp1)
                .category(Category.FOOD)
                .description("coupon1 desc")
                .title("Coupon1")
                .amount(20)
                .image("Image")
                .price(9.99)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate((Date.valueOf(LocalDate.now().plusDays(100))))
                .build();
        Coupon coupon2 = Coupon.builder()
                .company(comp1)
                .category(Category.PC)
                .description("coupon2 descr")
                .title("Coupon2")
                .amount(10)
                .image("Image")
                .price(99.99)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate((Date.valueOf(LocalDate.now().plusDays(100))))
                .build();
        Coupon coupon3 = Coupon.builder()
                .category(Category.VACATION)
                .description("coupon3 descr")
                .title("Coupon3")
                .amount(30)
                .image("Image")
                .price(1199.99)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate((Date.valueOf(LocalDate.now().plusDays(100))))
                .build();

        Coupon coupon4 = Coupon.builder()
                .category(Category.FOOD)
                .description("coupon4 descr")
                .title("Coupon4")
                .amount(60)
                .image("Image")
                .price(5.99)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate((Date.valueOf(LocalDate.now().plusDays(100))))
                .build();
        Coupon coupon5 = Coupon.builder()
                .category(Category.PC)
                .description("coupon5 descr")
                .title("Coupon5")
                .amount(25)
                .image("Image")
                .price(159.99)
                .startDate(Date.valueOf(LocalDate.now().plusDays(-10)))
                .endDate((Date.valueOf(LocalDate.now().plusDays(-1))))
                .build();
        Coupon coupon6 = Coupon.builder()
                .category(Category.VACATION)
                .description("coupon6 descr")
                .title("Coupon6")
                .amount(30)
                .image("Image")
                .price(1199.99)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate((Date.valueOf(LocalDate.now().plusDays(100))))
                .build();

        Coupon coupon7 = Coupon.builder()
                .category(Category.FOOD)
                .description("coupon7 descr")
                .title("coupon7")
                .amount(40)
                .image("Image")
                .price(7.99)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate((Date.valueOf(LocalDate.now().plusDays(100))))
                .build();
        Coupon coupon8 = Coupon.builder()
                .category(Category.PC)
                .description("coupon8 descr")
                .title("coupon8")
                .amount(25)
                .image("Image")
                .price(123.99)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate((Date.valueOf(LocalDate.now().plusDays(100))))
                .build();
        Coupon coupon9 = Coupon.builder()
                .category(Category.VACATION)
                .description("coupon9 descr")
                .title("Coupon9")
                .amount(30)
                .image("Image")
                .price(1200.00)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate((Date.valueOf(LocalDate.now().plusDays(100))))
                .build();

        couponRepository.saveAll(Arrays.asList(coupon1, coupon4,
                coupon2, coupon5, coupon8,
                coupon6, coupon9, coupon3));
        couponRepository.findAll().forEach(System.out::println);

        PrintUtils.printHeader("Update Coupon");
        coupon1.setAmount(coupon1.getAmount()-1);
        couponRepository.saveAndFlush(coupon1);
        couponRepository.findAll().forEach(System.out::println);

        PrintUtils.printHeader("Delete Coupon");
        couponRepository.delete(coupon9);
        couponRepository.findAll().forEach(System.out::println);

        //-----------------COMPANY with COUPONS-----------------------

        Company comp2 = companyRepository.findById(2);
        Company comp3 = companyRepository.findById(3);
        PrintUtils.printHeader("Save Company with Coupons");
        coupon1.setCompany(comp1);
        coupon2.setCompany(comp1);
        comp1.setCoupons(Arrays.asList(coupon1, coupon2));
        coupon4.setCompany(comp2);
        coupon5.setCompany(comp2);
        coupon6.setCompany(comp2);
        comp2.setCoupons(Arrays.asList(coupon4, coupon5,coupon6));
        coupon7.setCompany(comp3);
        coupon8.setCompany(comp3);
        comp3.setCoupons(Arrays.asList(coupon7, coupon8));

        companyRepository.saveAllAndFlush(Arrays.asList(comp1,comp2,comp3));
        PrintUtils.printTestHeader("Company 1 coupons:");
        comp1.getCoupons().forEach(System.out::println);

        PrintUtils.printHeader("Add new coupon to company coupons (companyId = 1)");
        Company company = companyRepository.getById(1);
        company.addToCoupon(coupon3);
        companyRepository.saveAndFlush(company);
        company.getCoupons().forEach(System.out::println);


        PrintUtils.printHeader("Delete Company with Coupons");
        companyRepository.delete(comp3);
        companyRepository.findAll().forEach(System.out::println);

        PrintUtils.printHeader("Company (id = 1), find company coupons");
        couponRepository.findAllCompanyCoupons(1).forEach(System.out::println);

        PrintUtils.printHeader("Company (id = 1), find by id");
        System.out.println( companyRepository.findById(1));

        PrintUtils.printHeader("Company find by email and password");
        System.out.println( companyRepository.findByEmailAndPassword("cola_updated@gmail.com", "1234"));

        PrintUtils.printHeader("Check if Coupon existst (title = Coupon1, companyId = 1)");
        System.out.println((couponRepository.getIdByTitleAndCompany(1,"Coupon1") != 0) ? "Yes" : "No");

        PrintUtils.printHeader("2Check if Coupon existst (title = Coupon1 xxx, companyId = 1)");
        System.out.println((companyRepository.getIdByTitleAndCompany(1,"Coupon1 xxx") != 0) ? "Yes" : "No");


        //-----------------customer save with COUPONS-----------------------

        PrintUtils.printHeader("Save Customer with Coupons");
        System.out.println("Add coupons to customer id = 1");
        System.out.println("Customer coupons before add:");
        Customer cust1 = customerRepository.getById(1);
        customerRepository.getById(cust1.getId()).getCoupons().forEach(System.out::println);

        cust1.setCoupons(List.of(coupon1,coupon2));
        customerRepository.saveAndFlush(cust1);
        System.out.println("Customer coupons after add");
        customerRepository.getById(cust1.getId()).getCoupons().forEach(System.out::println);

        //-----------------get expired Coupons-----------------------
        PrintUtils.printHeader("Get expired coupons");
        couponRepository.findByEndDateBefore(Date.valueOf(LocalDate.now())).forEach(System.out::println);
    }
}
