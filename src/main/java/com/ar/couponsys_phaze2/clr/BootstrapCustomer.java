package com.ar.couponsys_phaze2.clr;

import com.ar.couponsys_phaze2.beans.Customer;
import com.ar.couponsys_phaze2.repos.CustomerRepository;
import com.ar.couponsys_phaze2.utils.Art;
import com.ar.couponsys_phaze2.utils.PrintUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(2)
@RequiredArgsConstructor
public class BootstrapCustomer implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println();
        System.out.println(Art.CUSTOMER);

        PrintUtils.printHeader("Add Customer");

        Customer cust1 = Customer.builder()
                .email("moshe1@gmail.com")
                .password("1234")
                .firstName("moshe1")
                .lasttName("shasha1")
                .build();

        Customer cust2 = Customer.builder()
                .email("moshe2@gmail.com")
                .password("1234")
                .firstName("moshe2")
                .lasttName("shasha2")
                .build();

        Customer cust3 = Customer.builder()
                .email("moshe3@gmail.com")
                .password("1234")
                .firstName("moshe3")
                .lasttName("shasha3")
                .build();

        Customer cust4 = Customer.builder()
                .email("mosheToDelete3@gmail.com")
                .password("1234")
                .firstName("moshe3")
                .lasttName("shasha3")
                .build();

        customerRepository.saveAll(Arrays.asList(cust1, cust2, cust3, cust4));
        customerRepository.findAll().forEach(System.out::println);

        PrintUtils.printHeader("Update Customer (1)");
        cust1.setFirstName("moshe1_updated");
        customerRepository.saveAndFlush(cust1);
        customerRepository.findAll().forEach(System.out::println);

        PrintUtils.printHeader("Delete Customer");
        customerRepository.delete(cust4);
        customerRepository.findAll().forEach(System.out::println);

        PrintUtils.printHeader("Get Customer by mail and password");
        Customer customer = customerRepository.findByEmailAndPassword("moshe2@gmail.com", "1234");

        System.out.println(customer);
    }

}
