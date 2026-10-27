 package com.ecommerceoms.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Order {

    private String orderId;
    private String customerId;
    private String customerName;

    /*
     * Product ID -> Quantity
     */
    private Map<String, Integer> items;

    private double totalAmount;
    private String status;
    private Payment payment;
    private String orderDate;

    public Order() {
        items = new LinkedHashMap<>();
    }

    public Order(String orderId,
                 String customerId,
                 String customerName) {

        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.items = new LinkedHashMap<>();
        this.totalAmount = 0.0;
        this.status = "CREATED";
        this.orderDate =
                java.time.LocalDateTime.now().toString();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void addItem(String productId, int quantity) {

        items.put(
                productId,
                items.getOrDefault(productId, 0) + quantity
        );
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", items=" + items +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", payment=" + payment +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}