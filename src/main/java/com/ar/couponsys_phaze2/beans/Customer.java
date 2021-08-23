package com.ar.couponsys_phaze2.beans;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lasttName;
    private String email;
    private String password;
    @ManyToMany
    @Singular
    @ToString.Exclude
    private List<Coupon> coupons;

    public void addToCoupon(Coupon coupon) {
        this.coupons.add(coupon);

    }
}
