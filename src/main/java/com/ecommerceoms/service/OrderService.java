 package com.ecommerceoms.service;

import com.ecommerceoms.model.Order;
import com.ecommerceoms.model.Payment;
import com.ecommerceoms.model.Product;
import com.ecommerceoms.persistence.JsonManager;
import com.ecommerceoms.util.IdGenerator;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderService {

    private static final String ORDER_FILE =
            "orders.json";

    private final JsonManager jsonManager;
    private final List<Order> orders;
    private final ProductService productService;

    public OrderService(ProductService productService) {

        this.productService = productService;
        this.jsonManager = new JsonManager();

        List<Order> loadedOrders =
                jsonManager.load(
                        ORDER_FILE,
                        new TypeReference<List<Order>>() {
                        }
                );

        this.orders =
                loadedOrders != null
                        ? loadedOrders
                        : new ArrayList<>();
    }

    public Order createOrder(String customerId,
                             String customerName) {

        if (customerId == null ||
                customerId.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Customer ID cannot be empty."
            );
        }

        if (customerName == null ||
                customerName.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Customer name cannot be empty."
            );
        }

        Order order =
                new Order(
                        IdGenerator.generateOrderId(),
                        customerId,
                        customerName
                );

        orders.add(order);

        save();

        return order;
    }

    public boolean addProductToOrder(
            String orderId,
            String productId,
            int quantity) {

        if (quantity <= 0) {
            return false;
        }

        Order order =
                findOrderById(orderId);

        Product product =
                productService.findProductById(
                        productId
                );

        if (order == null ||
                product == null) {

            return false;
        }

        if (!"CREATED".equals(
                order.getStatus())) {

            return false;
        }

        if (product.getStock() < quantity) {

            return false;
        }

        boolean stockReduced =
                productService.reduceStock(
                        productId,
                        quantity
                );

        if (!stockReduced) {
            return false;
        }

        order.addItem(
                productId,
                quantity
        );

        order.setTotalAmount(
                calculateTotal(order)
        );

        save();

        return true;
    }

    public boolean makePayment(
            String orderId,
            String paymentMethod) {

        Order order =
                findOrderById(orderId);

        if (order == null) {
            return false;
        }

        if (order.getItems() == null ||
                order.getItems().isEmpty()) {

            return false;
        }

        if (paymentMethod == null ||
                paymentMethod.trim().isEmpty()) {

            return false;
        }

        Payment payment =
                new Payment(
                        IdGenerator.generatePaymentId(),
                        paymentMethod,
                        order.getTotalAmount(),
                        "SUCCESS",
                        java.time.LocalDateTime
                                .now()
                                .toString()
                );

        order.setPayment(payment);

        order.setStatus("PAID");

        save();

        return true;
    }

    public Order findOrderById(String orderId) {

        if (orderId == null) {
            return null;
        }

        for (Order order : orders) {

            if (order.getOrderId()
                    .equalsIgnoreCase(orderId)) {

                return order;
            }
        }

        return null;
    }

    public List<Order> getAllOrders() {

        return new ArrayList<>(orders);
    }

    public boolean updateStatus(
            String orderId,
            String status) {

        Order order =
                findOrderById(orderId);

        if (order == null ||
                status == null ||
                status.trim().isEmpty()) {

            return false;
        }

        order.setStatus(
                status.toUpperCase()
        );

        save();

        return true;
    }

    private double calculateTotal(Order order) {

        double total = 0.0;

        for (Map.Entry<String, Integer> entry :
                order.getItems().entrySet()) {

            Product product =
                    productService.findProductById(
                            entry.getKey()
                    );

            if (product != null) {

                total +=
                        product.getPrice()
                        * entry.getValue();
            }
        }

        return total;
    }

    private void save() {

        jsonManager.save(
                ORDER_FILE,
                orders
        );
    }
}