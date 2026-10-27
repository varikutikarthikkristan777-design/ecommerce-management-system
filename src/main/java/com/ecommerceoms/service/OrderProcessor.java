 package com.ecommerceoms.service;

import com.ecommerceoms.model.Order;

public class OrderProcessor {

    private final OrderService orderService;

    public OrderProcessor(OrderService orderService) {
        this.orderService = orderService;
    }

    public boolean processOrder(String orderId) {

        Order order =
                orderService.findOrderById(orderId);

        if (order == null) {
            return false;
        }

        if (order.getItems() == null ||
                order.getItems().isEmpty()) {

            return false;
        }

        if (!"PAID".equals(order.getStatus())) {

            return false;
        }

        return orderService.updateStatus(
                orderId,
                "PROCESSING"
        );
    }

    public boolean shipOrder(String orderId) {

        Order order =
                orderService.findOrderById(orderId);

        if (order == null) {
            return false;
        }

        if (!"PROCESSING".equals(
                order.getStatus())) {

            return false;
        }

        return orderService.updateStatus(
                orderId,
                "SHIPPED"
        );
    }

    public boolean deliverOrder(String orderId) {

        Order order =
                orderService.findOrderById(orderId);

        if (order == null) {
            return false;
        }

        if (!"SHIPPED".equals(
                order.getStatus())) {

            return false;
        }

        return orderService.updateStatus(
                orderId,
                "DELIVERED"
        );
    }

    public boolean cancelOrder(String orderId) {

        Order order =
                orderService.findOrderById(orderId);

        if (order == null) {
            return false;
        }

        if ("DELIVERED".equals(
                order.getStatus())) {

            return false;
        }

        return orderService.updateStatus(
                orderId,
                "CANCELLED"
        );
    }
}