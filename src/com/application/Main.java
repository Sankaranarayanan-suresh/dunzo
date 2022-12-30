package com.application;

import com.utils.Utils;

public class Main {
    public static void main(String[] args) {
        Application dunzo = new Application();

       mainFunction: while (true){
           System.out.println("\t\t\tWelcome to Dunzo application.");
            System.out.println("1.Customer\n2.Rider\n3.Admin\n4.Exit");
            int user = Utils.getInteger();
            switch (user){
                case 1:
                    dunzo.open("customer");
                    break;
                case 2:
                    dunzo.open("rider");
                    break;
                case 3:
                    dunzo.open("admin");
                    break;
                case 4:
                    dunzo.close();
                    break mainFunction;
                default:
                    System.out.println("Select any given option");
                    break ;
            }

        }
    }
}
