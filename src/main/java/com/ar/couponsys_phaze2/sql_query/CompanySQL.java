package com.ar.couponsys_phaze2.sql_query;

public class CompanySQL {
    public static final String QUERY_GET_ID_BY_TITLE_COMP = "Select count(*) from coupons cu where " +
            "exists(select 1 from companies_coupons cc where cc.company_id = :companyId) and " +
            " cu.title = :couponTitle";
}
