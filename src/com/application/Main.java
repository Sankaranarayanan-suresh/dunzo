package com.application;

import com.utils.Utils;

public class Main {
    public static void main(String[] args) {
       mainFunction: while (true){
           System.out.println("\t\t\tWelcome to Dunzo application.");
            System.out.println("1.Customer\n2.Rider\n3.Admin\n4.Exit");
            int user = Utils.getInteger();
            switch (user){
                case 1:
                    new Application().open("customer");
                    break;
                case 2:
                    new Application().open("rider");
                    break;
                case 3:
                    new Application().open("admin");
                    break;
                case 4:
                    new Application().close();
                    break mainFunction;
                default:
                    System.out.println("Select any given option");
                    break ;
            }

        }
    }
}
