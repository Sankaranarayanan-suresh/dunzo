package com.utils;

import sun.security.util.Length;

import java.util.Scanner;

public class Utils {
    public static int getInteger() {
        Scanner sc = new Scanner(System.in);
        try {
            return sc.nextInt();
        } catch (Exception e) {
            System.err.println("Enter valid option!!.");
            return getInteger();
        }
    }
    public static long getPhoneNumber() {
        Scanner sc = new Scanner(System.in);
        try {
            long phNumber = sc.nextLong();
            if (String.valueOf(phNumber).length() == 10){
                return phNumber;
            }else {
                System.err.print("Enter a valid PhoneNumber!!!.");
                return getPhoneNumber();
            }
        } catch (Exception e) {
            System.err.print("Enter a valid PhoneNumber!!!.");
            return getPhoneNumber();
        }
    }
}
