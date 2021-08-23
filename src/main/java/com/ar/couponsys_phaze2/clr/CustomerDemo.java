package com.ar.couponsys_phaze2.clr;

import com.ar.couponsys_phaze2.beans.Category;
import com.ar.couponsys_phaze2.beans.Coupon;
import com.ar.couponsys_phaze2.exceptions.CouponSystemException;
import com.ar.couponsys_phaze2.repos.CouponRepository;
import com.ar.couponsys_phaze2.security.ClientType;
import com.ar.couponsys_phaze2.security.LoginManager;
import com.ar.couponsys_phaze2.serveces.CompanyService;
import com.ar.couponsys_phaze2.serveces.CompanyServiceImpl;
import com.ar.couponsys_phaze2.serveces.CustomerService;
import com.ar.couponsys_phaze2.serveces.CustomerServiecImpl;
import com.ar.couponsys_phaze2.utils.Art;
import com.ar.couponsys_phaze2.utils.PrintUtils;
import com.ar.couponsys_phaze2.utils.TablePrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5)
@RequiredArgsConstructor
public class CustomerDemo implements CommandLineRunner {

    private final LoginManager loginManager;
    private final CouponRepository couponRepository;

    @Autowired
    private CustomerService customerService;

    @Override
    public void run(String... args) throws Exception {
        //------------------LOGIN-------------------------------
        System.out.println();
        System.out.println(Art.CUSTOMER_SERVICE);

        PrintUtils.printHeader("Customer bad login (moshe.com,1234)");
        customerService = (CustomerService) loginManager.login("moshe.com", "1234", ClientType.Customer);
        PrintUtils.printLoginResult(customerService);

        PrintUtils.printHeader("Customer good login (moshe2@gmail.com,1234)");

        customerService = (CustomerService) loginManager.login("moshe2@gmail.com", "1234", ClientType.Customer);
        PrintUtils.printLoginResult(customerService);

        //------------------GetCustomerDetails-------------------------------
        PrintUtils.printHeader("Customer details");
        TablePrinter.print(customerService.getCustomerDetails());

        //------------------CustomerPurchaseCoupon-------------------------------

        PrintUtils.printHeader("Customer purchase coupon");

        System.out.println("Customer coupons before purchase: ");
        TablePrinter.print(customerService.getCustomerPurchases());

        Coupon coupon = couponRepository.getById(3);
        System.out.println("Coupon to purchase:");
        TablePrinter.print(coupon);
        customerService.addCouponPurchase(coupon);

        System.out.println("Customer coupons after purchase: ");
        TablePrinter.print(customerService.getCustomerPurchases());

        //------------------CustomerPurchase the same Coupon-------------------------------
        PrintUtils.printHeader("Customer try to purchase the same coupon");

        try {
            customerService.addCouponPurchase(coupon);
        } catch ( CouponSystemException ex) {
            System.out.println(ex.getMessage());
            System.out.println();
        }

        //------------------CustomerPurchase Expired Coupon-------------------------------
        PrintUtils.printHeader("Customer try to purchase expired coupon (4)");

        Coupon coupon4 = couponRepository.getById(4);
        System.out.println("Coupon to purchase:");
        TablePrinter.print(coupon4);
        try {
            customerService.addCouponPurchase(coupon4);
        } catch ( CouponSystemException ex) {
            System.out.println(ex.getMessage());
            System.out.println();
        }

        //--------------------------
        PrintUtils.printHeader("Get all Customer coupons");

        Coupon coupon1 = couponRepository.getById(1);
        customerService.addCouponPurchase(coupon1);
        Coupon coupon2 = couponRepository.getById(2);
        customerService.addCouponPurchase(coupon2);
        Coupon coupon6 = couponRepository.getById(6);
        customerService.addCouponPurchase(coupon6);
        Coupon coupon8 = couponRepository.getById(8);
        customerService.addCouponPurchase(coupon8);

        TablePrinter.print(customerService.getCustomerPurchases());

        PrintUtils.printHeader("Get Customer coupons by Category FOOD");
        TablePrinter.print(customerService.getCustomerPurchases(Category.FOOD));

        PrintUtils.printHeader("Get Customer coupons by Category PC");
        TablePrinter.print(customerService.getCustomerPurchases(Category.PC));

        PrintUtils.printHeader("Get Customer coupons with price < 100");
        TablePrinter.print(customerService.getCustomerPurchases(100));
    }
}
