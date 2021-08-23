package com.ar.couponsys_phaze2.repos;

import com.ar.couponsys_phaze2.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, int id);
    Customer findByEmailAndPassword(String email, String password);
    Customer findById(int id);

}
