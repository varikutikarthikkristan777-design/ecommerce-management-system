 package com.ecommerceoms.service;

import com.ecommerceoms.model.Product;
import com.ecommerceoms.persistence.JsonManager;
import com.ecommerceoms.util.IdGenerator;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private static final String PRODUCT_FILE =
            "products.json";

    private final JsonManager jsonManager;
    private final List<Product> products;

    public ProductService() {

        jsonManager = new JsonManager();

        List<Product> loadedProducts =
                jsonManager.load(
                        PRODUCT_FILE,
                        new TypeReference<List<Product>>() {
                        }
                );

        products =
                loadedProducts != null
                        ? loadedProducts
                        : new ArrayList<>();
    }

    public Product addProduct(String name,
                              double price,
                              int stock) {

        if (name == null ||
                name.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Product name cannot be empty."
            );
        }

        if (price < 0) {

            throw new IllegalArgumentException(
                    "Price cannot be negative."
            );
        }

        if (stock < 0) {

            throw new IllegalArgumentException(
                    "Stock cannot be negative."
            );
        }

        Product product =
                new Product(
                        IdGenerator.generateProductId(),
                        name.trim(),
                        price,
                        stock
                );

        products.add(product);

        save();

        return product;
    }

    public List<Product> getAllProducts() {

        return new ArrayList<>(products);
    }

    public Product findProductById(String productId) {

        if (productId == null) {
            return null;
        }

        for (Product product : products) {

            if (product.getId()
                    .equalsIgnoreCase(productId)) {

                return product;
            }
        }

        return null;
    }

    public boolean reduceStock(String productId,
                               int quantity) {

        Product product =
                findProductById(productId);

        if (product == null) {
            return false;
        }

        if (quantity <= 0) {
            return false;
        }

        if (product.getStock() < quantity) {
            return false;
        }

        product.setStock(
                product.getStock() - quantity
        );

        save();

        return true;
    }

    public boolean increaseStock(String productId,
                                 int quantity) {

        Product product =
                findProductById(productId);

        if (product == null ||
                quantity <= 0) {

            return false;
        }

        product.setStock(
                product.getStock() + quantity
        );

        save();

        return true;
    }

    public boolean updateStock(String productId,
                               int stock) {

        Product product =
                findProductById(productId);

        if (product == null ||
                stock < 0) {

            return false;
        }

        product.setStock(stock);

        save();

        return true;
    }

    private void save() {

        jsonManager.save(
                PRODUCT_FILE,
                products
        );
    }
}