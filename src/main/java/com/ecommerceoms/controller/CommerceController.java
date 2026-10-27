 package com.ecommerceoms.controller;

import com.ecommerceoms.model.Customer;
import com.ecommerceoms.model.Order;
import com.ecommerceoms.model.Product;
import com.ecommerceoms.service.OrderProcessor;
import com.ecommerceoms.service.OrderService;
import com.ecommerceoms.service.ProductService;
import com.ecommerceoms.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommerceController {

    private final Scanner scanner;

    private final ProductService productService;
    private final OrderService orderService;
    private final OrderProcessor orderProcessor;

    private final List<Customer> customers;

    public CommerceController() {

        scanner = new Scanner(System.in);

        productService =
                new ProductService();

        orderService =
                new OrderService(
                        productService
                );

        orderProcessor =
                new OrderProcessor(
                        orderService
                );

        customers =
                new ArrayList<>();
    }

    public void start() {

        boolean running = true;

        while (running) {

            displayMenu();

            int choice =
                    readInt(
                            "Enter your choice: "
                    );

            try {

                switch (choice) {

                    case 1:
                        addProduct();
                        break;

                    case 2:
                        displayProducts();
                        break;

                    case 3:
                        createCustomer();
                        break;

                    case 4:
                        displayCustomers();
                        break;

                    case 5:
                        createOrder();
                        break;

                    case 6:
                        addProductToOrder();
                        break;

                    case 7:
                        makePayment();
                        break;

                    case 8:
                        processOrder();
                        break;

                    case 9:
                        shipOrder();
                        break;

                    case 10:
                        deliverOrder();
                        break;

                    case 11:
                        displayOrders();
                        break;

                    case 12:
                        cancelOrder();
                        break;

                    case 13:
                        running = false;

                        System.out.println();
                        System.out.println(
                                "Thank you for using " +
                                "E-Commerce Order Management System!"
                        );
                        break;

                    default:
                        System.out.println(
                                "Invalid choice."
                        );
                }

            } catch (Exception e) {

                System.out.println(
                        "Error: " +
                        e.getMessage()
                );
            }
        }

        scanner.close();
    }

    private void displayMenu() {

        System.out.println();
        System.out.println(
                "================================================"
        );
        System.out.println(
                "       E-COMMERCE ORDER MANAGEMENT SYSTEM"
        );
        System.out.println(
                "================================================"
        );

        System.out.println(
                "1.  Add Product"
        );

        System.out.println(
                "2.  Display Products"
        );

        System.out.println(
                "3.  Create Customer"
        );

        System.out.println(
                "4.  Display Customers"
        );

        System.out.println(
                "5.  Create Order"
        );

        System.out.println(
                "6.  Add Product To Order"
        );

        System.out.println(
                "7.  Make Payment"
        );

        System.out.println(
                "8.  Process Order"
        );

        System.out.println(
                "9.  Ship Order"
        );

        System.out.println(
                "10. Deliver Order"
        );

        System.out.println(
                "11. Display Orders"
        );

        System.out.println(
                "12. Cancel Order"
        );

        System.out.println(
                "13. Exit"
        );

        System.out.println(
                "================================================"
        );
    }

    private void addProduct() {

        System.out.println(
                "\n--- ADD PRODUCT ---"
        );

        System.out.print(
                "Enter product name: "
        );

        String name =
                scanner.nextLine();

        double price =
                readDouble(
                        "Enter product price: "
                );

        int stock =
                readInt(
                        "Enter product stock: "
                );

        Product product =
                productService.addProduct(
                        name,
                        price,
                        stock
                );

        System.out.println(
                "\nProduct added successfully!"
        );

        System.out.println(
                "Product ID: " +
                product.getId()
        );
    }

    private void displayProducts() {

        System.out.println(
                "\n--- PRODUCTS ---"
        );

        List<Product> products =
                productService.getAllProducts();

        if (products.isEmpty()) {

            System.out.println(
                    "No products available."
            );

            return;
        }

        for (Product product :
                products) {

            System.out.println(
                    "ID     : " +
                    product.getId()
            );

            System.out.println(
                    "Name   : " +
                    product.getName()
            );

            System.out.println(
                    "Price  : ₹" +
                    product.getPrice()
            );

            System.out.println(
                    "Stock  : " +
                    product.getStock()
            );

            System.out.println(
                    "--------------------------------"
            );
        }
    }

    private void createCustomer() {

        System.out.println(
                "\n--- CREATE CUSTOMER ---"
        );

        System.out.print(
                "Enter customer name: "
        );

        String name =
                scanner.nextLine();

        System.out.print(
                "Enter email: "
        );

        String email =
                scanner.nextLine();

        System.out.print(
                "Enter phone: "
        );

        String phone =
                scanner.nextLine();

        System.out.print(
                "Enter address: "
        );

        String address =
                scanner.nextLine();

        Customer customer =
                new Customer(
                        IdGenerator.generateCustomerId(),
                        name,
                        email,
                        phone,
                        address
                );

        customers.add(customer);

        System.out.println(
                "\nCustomer created successfully!"
        );

        System.out.println(
                "Customer ID: " +
                customer.getId()
        );
    }

    private void displayCustomers() {

        System.out.println(
                "\n--- CUSTOMERS ---"
        );

        if (customers.isEmpty()) {

            System.out.println(
                    "No customers available."
            );

            return;
        }

        for (Customer customer :
                customers) {

            System.out.println(
                    "Customer ID : " +
                    customer.getId()
            );

            System.out.println(
                    "Name        : " +
                    customer.getName()
            );

            System.out.println(
                    "Email       : " +
                    customer.getEmail()
            );

            System.out.println(
                    "Phone       : " +
                    customer.getPhone()
            );

            System.out.println(
                    "Address     : " +
                    customer.getAddress()
            );

            System.out.println(
                    "--------------------------------"
            );
        }
    }

    private void createOrder() {

        System.out.println(
                "\n--- CREATE ORDER ---"
        );

        if (customers.isEmpty()) {

            System.out.println(
                    "Please create a customer first."
            );

            return;
        }

        displayCustomers();

        System.out.print(
                "Enter Customer ID: "
        );

        String customerId =
                scanner.nextLine();

        Customer customer =
                findCustomer(customerId);

        if (customer == null) {

            System.out.println(
                    "Customer not found."
            );

            return;
        }

        Order order =
                orderService.createOrder(
                        customer.getId(),
                        customer.getName()
                );

        System.out.println(
                "\nOrder created successfully!"
        );

        System.out.println(
                "Order ID: " +
                order.getOrderId()
        );
    }

    private void addProductToOrder() {

        System.out.println(
                "\n--- ADD PRODUCT TO ORDER ---"
        );

        System.out.print(
                "Enter Order ID: "
        );

        String orderId =
                scanner.nextLine();

        System.out.print(
                "Enter Product ID: "
        );

        String productId =
                scanner.nextLine();

        int quantity =
                readInt(
                        "Enter quantity: "
                );

        boolean success =
                orderService.addProductToOrder(
                        orderId,
                        productId,
                        quantity
                );

        if (success) {

            System.out.println(
                    "Product added to order successfully."
            );

        } else {

            System.out.println(
                    "Unable to add product."
            );
        }
    }

    private void makePayment() {

        System.out.println(
                "\n--- PAYMENT ---"
        );

        System.out.print(
                "Enter Order ID: "
        );

        String orderId =
                scanner.nextLine();

        System.out.print(
                "Enter payment method " +
                "(UPI/Card/Cash): "
        );

        String method =
                scanner.nextLine();

        boolean success =
                orderService.makePayment(
                        orderId,
                        method
                );

        if (success) {

            System.out.println(
                    "Payment successful."
            );

        } else {

            System.out.println(
                    "Payment failed."
            );
        }
    }

    private void processOrder() {

        System.out.println(
                "\n--- PROCESS ORDER ---"
        );

        System.out.print(
                "Enter Order ID: "
        );

        String orderId =
                scanner.nextLine();

        boolean success =
                orderProcessor.processOrder(
                        orderId
                );

        if (success) {

            System.out.println(
                    "Order is now PROCESSING."
            );

        } else {

            System.out.println(
                    "Order cannot be processed."
            );
        }
    }

    private void shipOrder() {

        System.out.println(
                "\n--- SHIP ORDER ---"
        );

        System.out.print(
                "Enter Order ID: "
        );

        String orderId =
                scanner.nextLine();

        boolean success =
                orderProcessor.shipOrder(
                        orderId
                );

        if (success) {

            System.out.println(
                    "Order shipped successfully."
            );

        } else {

            System.out.println(
                    "Order cannot be shipped."
            );
        }
    }

    private void deliverOrder() {

        System.out.println(
                "\n--- DELIVER ORDER ---"
        );

        System.out.print(
                "Enter Order ID: "
        );

        String orderId =
                scanner.nextLine();

        boolean success =
                orderProcessor.deliverOrder(
                        orderId
                );

        if (success) {

            System.out.println(
                    "Order delivered successfully."
            );

        } else {

            System.out.println(
                    "Order cannot be delivered."
            );
        }
    }

    private void displayOrders() {

        System.out.println(
                "\n--- ORDERS ---"
        );

        List<Order> orders =
                orderService.getAllOrders();

        if (orders.isEmpty()) {

            System.out.println(
                    "No orders available."
            );

            return;
        }

        for (Order order :
                orders) {

            System.out.println(
                    "Order ID      : " +
                    order.getOrderId()
            );

            System.out.println(
                    "Customer ID   : " +
                    order.getCustomerId()
            );

            System.out.println(
                    "Customer Name : " +
                    order.getCustomerName()
            );

            System.out.println(
                    "Items         : " +
                    order.getItems()
            );

            System.out.println(
                    "Total Amount  : ₹" +
                    order.getTotalAmount()
            );

            System.out.println(
                    "Status        : " +
                    order.getStatus()
            );

            System.out.println(
                    "Order Date    : " +
                    order.getOrderDate()
            );

            if (order.getPayment() != null) {

                System.out.println(
                        "Payment       : " +
                        order.getPayment()
                );
            }

            System.out.println(
                    "--------------------------------"
            );
        }
    }

    private void cancelOrder() {

        System.out.println(
                "\n--- CANCEL ORDER ---"
        );

        System.out.print(
                "Enter Order ID: "
        );

        String orderId =
                scanner.nextLine();

        boolean success =
                orderProcessor.cancelOrder(
                        orderId
                );

        if (success) {

            System.out.println(
                    "Order cancelled successfully."
            );

        } else {

            System.out.println(
                    "Order cannot be cancelled."
            );
        }
    }

    private Customer findCustomer(
            String customerId) {

        for (Customer customer :
                customers) {

            if (customer.getId()
                    .equalsIgnoreCase(customerId)) {

                return customer;
            }
        }

        return null;
    }

    private int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid integer."
                );
            }
        }
    }

    private double readDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Double.parseDouble(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }
}