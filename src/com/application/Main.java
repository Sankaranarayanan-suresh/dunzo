package com.application;

import com.application.Application;
import com.utils.Utils;

public class Main {
    public static void main(String[] args) {
        while (true){
            Application dunzo = new Application();
            System.out.println("1.Customer\n2.Rider\n3.Admin");
            int user = Utils.getInteger();
            dunzo.open("customer");

        }
    }
}
