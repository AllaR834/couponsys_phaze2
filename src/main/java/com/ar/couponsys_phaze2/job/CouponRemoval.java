package com.ar.couponsys_phaze2.job;

import com.ar.couponsys_phaze2.repos.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CouponRemoval {
    private final CouponRepository couponRepository;
    private final int sec3 = 1000*3;
    private final int day = 1000*60*60*24;
    @Scheduled(fixedRate = sec3)
    public void  deleteExpiredCoupons(){

        System.out.println("*********Scheduled Job*********");
        couponRepository.findByEndDateBefore(Date.valueOf(LocalDate.now())).forEach(System.out::println);
        couponRepository.deleteExpiredCoupons();
        couponRepository.findByEndDateBefore(Date.valueOf(LocalDate.now())).forEach(System.out::println);
        System.out.println("*********End*********");
    }
}
