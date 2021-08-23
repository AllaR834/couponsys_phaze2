package com.ar.couponsys_phaze2.beans;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "coupons")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Coupon {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.ORDINAL)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)        //
    @ToString.Exclude
    private Company company;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;
}
