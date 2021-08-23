package com.ar.couponsys_phaze2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CouponsysPhaze2Application {

	public static void main(String[] args) {
		SpringApplication.run(CouponsysPhaze2Application.class, args);

		System.out.println("Coupon system is running");
	}

}
