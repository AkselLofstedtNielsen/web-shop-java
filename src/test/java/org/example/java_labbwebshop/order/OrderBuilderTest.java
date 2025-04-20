package org.example.java_labbwebshop.order;

import org.example.java_labbwebshop.order.model.Order;
import org.example.java_labbwebshop.user.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrderBuilderTest {

    @Test
    public void testOrderBuilderWithExplicitFields() {
        // Create a test user
        User user = User.builder()
                .email("test@example.com")
                .password("Test1234")
                .build();

        // Create an order with explicit orderDate and status
        LocalDateTime now = LocalDateTime.now();
        Order order = Order.builder()
                .user(user)
                .orderItems(new ArrayList<>())
                .orderDate(now)
                .status(OrderStatus.PENDING)
                .build();

        // Verify that orderDate and status are set correctly
        assertNotNull(order.getOrderDate(), "Order date should not be null");
        assertEquals(now, order.getOrderDate(), "Order date should match the provided value");
        assertNotNull(order.getStatus(), "Status should not be null");
        assertEquals(OrderStatus.PENDING, order.getStatus(), "Status should be PENDING");
    }

    @Test
    public void testOrderBuilderWithoutExplicitFields() {
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

        // Assert that orderDate and status are null
        // This will fail if default values are being applied
        assertNull(order.getOrderDate(), "Order date should be null when not explicitly set");
        assertNull(order.getStatus(), "Status should be null when not explicitly set");
    }
}
