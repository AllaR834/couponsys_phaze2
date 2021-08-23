package com.ar.couponsys_phaze2.exceptions;

public class CouponSystemException extends Exception{
    public CouponSystemException(ErrMsg errMsg){
        super(errMsg.getDesc());
    }

}
