package com.ar.couponsys_phaze2.exceptions;

public enum ErrMsg {
    COMPANY_NAME_EXISTS("company name already exist"),
    COMPANY_EMAIL_EXISTS("company email already exist"),
    CUSTOMER_EMAIL_EXISTS("customer email already exist"),
    COMPANY_NOT_EXISTS("company id not exist"),
    CUSTOMER_NOT_EXISTS("customer id not exist"),
    COMPANY_NAME_NOT_EDITABLE("can not update Company name"),
    WRONG_LOGIN("wrong email or password"),
    GOOD_LOGIN("successfully login"),
    COUPON_EXISTS("coupon with title exists"),
    COUPON_TITLE_NOT_EDITABLE("can not update coupon title"),
    COUPON_NOT_EXISTS("can not update coupon title"),
    COUPON_COMPANY_NOT_EDITABLE("can not update coupon company"),
    PURCHASE_EXISTS("customer has this coupon already"),
    COUPON_NOT_AVAILABLE("the item is sold out"),
    COUPON_EXPIRED("Expired coupon can't be purchased")
    ;



    private String desc;

    ErrMsg(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
