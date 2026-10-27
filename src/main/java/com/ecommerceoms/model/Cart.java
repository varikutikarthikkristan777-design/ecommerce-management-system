 package com.ecommerceoms.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

    private Map<String, Integer> items;

    public Cart() {
        items = new LinkedHashMap<>();
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    public void addItem(String productId, int quantity) {

        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Product ID cannot be empty."
            );
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero."
            );
        }

        items.put(
                productId,
                items.getOrDefault(productId, 0) + quantity
        );
    }

    public boolean removeItem(String productId) {

        if (!items.containsKey(productId)) {
            return false;
        }

        items.remove(productId);
        return true;
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}