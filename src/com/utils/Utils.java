package com.utils;
import java.util.InputMismatchException;
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
    public static double getRatings() {
        Scanner sc = new Scanner(System.in);
        try {
            double rating = sc.nextDouble();
            if (rating > 5) {
                throw new RatingErrorException("Ratings should be between 0-5");
            }
            return rating;
        } catch (InputMismatchException e) {
            System.err.println("Enter valid option!!.");
            return getRatings();
        } catch (RatingErrorException e) {
            System.err.println(e.getMessage());
            return getRatings();
        }
    }
    public static double getAmount(){
        Scanner sc = new Scanner(System.in);
        double x ;
        try {
            x = sc.nextDouble();
            if(x<=0){
                throw new Exception();
            }
            return x;
        }
        catch (Exception e){
            System.out.println("Enter correct Amount!!!.");
            return getAmount();
        }
    }
}


