package com.ar.couponsys_phaze2.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "company")
    @Singular
    @ToString.Exclude
    private List<Coupon> coupons = new ArrayList<>();

    public void addToCoupon(Coupon coupon) {
        this.coupons.add(coupon);
        coupon.setCompany(this);
    }
}
