package org.example.java_labbwebshop.order.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.java_labbwebshop.cart.CartService;
import org.example.java_labbwebshop.cart.model.CartItem;
import org.example.java_labbwebshop.order.model.Order;
import org.example.java_labbwebshop.order.model.OrderItem;
import org.example.java_labbwebshop.order.repositories.OrderRepository;
import org.example.java_labbwebshop.user.User;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    @Transactional
    public Order placeOrder(User user) {
        List<CartItem> cartItems = cartService.getCartItems();

        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> OrderItem.builder()
                        .product(cartItem.getProduct())
                        .quantity(cartItem.getQuantity())
                        .build())
                .toList();

        Order order = Order.builder()
                .user(user)
                .orderItems(orderItems)
                .orderDate(java.time.LocalDateTime.now())
                .status(org.example.java_labbwebshop.order.OrderStatus.PENDING)
                .build();

// Sätter relationen från OrderItem tillbaka till Order
        order.getOrderItems().forEach(item -> item.setOrder(order));

// Spara ordern och alla items
        orderRepository.save(order);

        cartService.clearCart();

        return order;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    public BigDecimal calculateTotalPrice(Order order) {
        return order.getOrderItems().stream()
                .map(orderItem -> orderItem.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
