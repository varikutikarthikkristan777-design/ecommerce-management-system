 package com.ecommerceoms.util;

import java.util.UUID;

public final class IdGenerator {

    private IdGenerator() {
        // Prevent object creation
    }

    public static String generateProductId() {
        return "P-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    public static String generateCustomerId() {
        return "C-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    public static String generateOrderId() {
        return "O-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    public static String generatePaymentId() {
        return "PAY-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }
}