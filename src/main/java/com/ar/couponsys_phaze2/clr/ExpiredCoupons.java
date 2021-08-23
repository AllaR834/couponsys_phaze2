package com.ar.couponsys_phaze2.clr;

import com.ar.couponsys_phaze2.beans.Category;
import com.ar.couponsys_phaze2.beans.Coupon;
import com.ar.couponsys_phaze2.repos.CouponRepository;
import com.ar.couponsys_phaze2.utils.PrintUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Order(7)
public class ExpiredCoupons implements CommandLineRunner {
    private final CouponRepository couponRepository;

    @Override
    public void run(String... args) throws Exception {

        PrintUtils.printHeader("Add expired coupons");

        Coupon coupon1 = Coupon.builder()
                .category(Category.PC)
                .description("expired1")
                .title("expired1")
                .amount(25)
                .image("Image")
                .price(159.99)
                .startDate(Date.valueOf(LocalDate.now().plusDays(-10)))
                .endDate((Date.valueOf(LocalDate.now().plusDays(-1))))
                .build();
        Coupon coupon2 = Coupon.builder()
                .category(Category.PC)
                .description("expired2")
                .title("expired2")
                .amount(10)
                .image("Image")
                .price(25.6)
                .startDate(Date.valueOf(LocalDate.now().plusDays(-10)))
                .endDate((Date.valueOf(LocalDate.now().plusDays(-1))))
                .build();
        Coupon coupon3 = Coupon.builder()
                .category(Category.PC)
                .description("expired3")
                .title("expired3")
                .amount(3)
                .image("Image")
                .price(12.8)
                .startDate(Date.valueOf(LocalDate.now().plusDays(-10)))
                .endDate((Date.valueOf(LocalDate.now().plusDays(-1))))
                .build();

        couponRepository.saveAll(Arrays.asList(coupon1,coupon2, coupon3));
        couponRepository.findByEndDateBefore(Date.valueOf(LocalDate.now())).forEach(System.out::println);

    }
}
