 package com.ecommerceoms;

import com.ecommerceoms.controller.CommerceController;

public class Main {

    public static void main(String[] args) {

        System.out.println();
        System.out.println(
                "Starting E-Commerce Order Management System..."
        );

        CommerceController controller =
                new CommerceController();

        controller.start();
    }
}