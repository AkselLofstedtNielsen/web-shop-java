package org.example.java_labbwebshop.order;

import org.example.java_labbwebshop.order.model.Order;
import org.example.java_labbwebshop.user.User;
import java.util.ArrayList;

public class OrderBuilderMain {
    public static void main(String[] args) {
        // Create a test user
        User user = User.builder()
                .email("test@example.com")
                .password("Test1234")
                .build();
        
        // Create an order without explicit orderDate and status
        Order order = Order.builder()
                .user(user)
                .orderItems(new ArrayList<>())
                .build();
        
        // Print the values of orderDate and status
        System.out.println("Order date: " + order.getOrderDate());
        System.out.println("Order status: " + order.getStatus());
    }
}