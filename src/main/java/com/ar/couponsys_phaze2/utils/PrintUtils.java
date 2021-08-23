package com.ar.couponsys_phaze2.utils;

import com.ar.couponsys_phaze2.exceptions.ErrMsg;
import com.ar.couponsys_phaze2.serveces.ClientService;

public class PrintUtils {
    private static int countTest = 1;

    public PrintUtils() {
    }

    public static void printTestHeader(String testHeader) {
        printLine();
        System.out.println(testHeader);
    }

    public static void printLine() {
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
    }

    public static void printHeader(String header) {
        System.out.println();
        printLine();
        System.out.printf("             Test #:%d - Name: %s%n", countTest++, header);
        printLine();
    }

    public static void printLoginResult(Object service) {
        if (service == null) {
            System.out.println(ErrMsg.WRONG_LOGIN.getDesc());
        } else {
            System.out.println(ErrMsg.GOOD_LOGIN.getDesc());
        }
    }

}
