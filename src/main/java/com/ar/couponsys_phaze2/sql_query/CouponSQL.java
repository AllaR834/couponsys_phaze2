package com.ar.couponsys_phaze2.sql_query;

public class CouponSQL {
    public static final String QUERY_GET_ID_BY_TITLE_COMP =
            "Select count(*) from coupons cu where " +
                    "exists(select 1 from companies_coupons cc where cc.company_id = :companyId) and " +
                    " cu.title = :couponTitle";
    public static final String QUERY_GET_PURCHASE =
            "select count(*) from customers_coupons where customer_id = :customerId and coupons_id = :couponId";

    public static final String QUERY_DELETE_PURCHASES =
            "delete cc from customers_coupons cc inner join coupons cu on cc.coupons_id = cu.id where cu.company_id = :companyId";

    public static final String QUERY_DELETE_EXPIRED =
            "delete from coupons cu where end_date < now()"; //SET SQL_SAFE_UPDATES=1;

    public static final String QUERY_FIND_COMPANY_COUPONS =
            "Select * from coupons cu left join companies_coupons cc on  cu.id = cc.coupons_id " +
                    "where cc.company_id = :companyId order by title";

    public static final String QUERY_FIND_BY_CATEGORY_CUSTOMER =
            "Select * from coupons cu left join customers_coupons cc on  cu.id = cc.coupons_id " +
                    "where cc.customer_id = :customerid and cu.category = :categoryid";

    public static final String QUERY_FIND_BY_CUSTOMER_PRICE =
            "Select * from coupons cu left join customers_coupons cc on  cu.id = cc.coupons_id " +
                    "where cc.customer_id = :customerid and cu.price < :maxprice";

}
