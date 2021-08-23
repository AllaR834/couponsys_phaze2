package com.ar.couponsys_phaze2.clr;

import com.ar.couponsys_phaze2.beans.Category;
import com.ar.couponsys_phaze2.beans.Coupon;
import com.ar.couponsys_phaze2.exceptions.CouponSystemException;
import com.ar.couponsys_phaze2.security.ClientType;
import com.ar.couponsys_phaze2.security.LoginManager;
import com.ar.couponsys_phaze2.serveces.CompanyService;
import com.ar.couponsys_phaze2.serveces.CompanyServiceImpl;
import com.ar.couponsys_phaze2.serveces.CustomerService;
import com.ar.couponsys_phaze2.utils.Art;
import com.ar.couponsys_phaze2.utils.PrintUtils;
import com.ar.couponsys_phaze2.utils.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
@Order(4)
@RequiredArgsConstructor
public class CompanyDemo implements CommandLineRunner {

    private final LoginManager loginManager;

    @Autowired
    private CompanyService companyService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println();
        System.out.println(Art.COMPANY_SERVICE);

        PrintUtils.printHeader("Company bad login (cola.@gmail.com,1234)");
        companyService = (CompanyService) loginManager.login("cola.@gmail.com", "1234", ClientType.Company);
        PrintUtils.printLoginResult(companyService);

        PrintUtils.printHeader("Company good login (cola_updated@gmail.com,1234)");

        companyService = (CompanyService) loginManager.login("cola_updated@gmail.com", "1234", ClientType.Company);
        PrintUtils.printLoginResult(companyService);

        //------------------Get Company Details-------------------------------
        PrintUtils.printHeader("Company details");
        System.out.println(companyService.getCompanyDetails());

        //---------"Get Company coupons---------------
        PrintUtils.printHeader("Get Company coupons");
        //companyService.getCompanyCoupons().forEach(System.out::println);
        TablePrinter.print(companyService.getCompanyCoupons());

        //---------"Company add coupon---------------
        PrintUtils.printHeader("Company add coupon");

        Coupon coupon = Coupon.builder()
                .category(Category.FOOD)
                .description("coupon10 descr")
                .title("Coupon10")
                .amount(30)
                .image("Image")
                .price(7.50)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate((Date.valueOf(LocalDate.now().plusDays(100))))
                .build();
        companyService.addCoupon(coupon);

        TablePrinter.print(companyService.getCompanyCoupons());

        //---------"Company add the same coupon---------------
        PrintUtils.printHeader("Company add the same coupon");
        try {
            companyService.addCoupon(coupon);
        } catch (CouponSystemException ex) {
            System.out.println(ex.getMessage());
            System.out.println();
        }
        TablePrinter.print(companyService.getCompanyCoupons());

        //---------"Get company coupon by id(=1)---------------
        PrintUtils.printHeader("Get Company coupon by id = 1");
        TablePrinter.print(companyService.getCompanyCoupon(1));

        //---------"Company update coupon---------------
        PrintUtils.printHeader("Company update coupon1 - price");
        Coupon toUpdate = companyService.getCompanyCoupon(1);
        toUpdate.setPrice(8.5);
        try {
            companyService.updateCoupon(1, toUpdate);
        } catch (CouponSystemException ex) {
            System.out.println(ex.getMessage());
        }
        TablePrinter.print(companyService.getCompanyCoupon(1));

        //---------"Company try update coupon's title---------------
        PrintUtils.printHeader("Company update coupon1 - title");
        toUpdate.setTitle("xxxx");
        try {
            companyService.updateCoupon(1, toUpdate);
        } catch (CouponSystemException ex) {
            System.out.println(ex.getMessage());
            System.out.println();
        }
        TablePrinter.print(companyService.getCompanyCoupons());

        //---------"Company delete coupon---------------
        PrintUtils.printHeader("Company delete coupon");
        companyService.deleteCoupon(11);
        TablePrinter.print(companyService.getCompanyCoupons());

        //----------------------------------------
        PrintUtils.printHeader("Get Company coupons by Category");
        TablePrinter.print(companyService.getCompanyCoupons(Category.FOOD));

        //----------------------------------------
        PrintUtils.printHeader("Get Company coupons with price < 100");
        TablePrinter.print(companyService.getCompanyCoupons(100));
    }
}
