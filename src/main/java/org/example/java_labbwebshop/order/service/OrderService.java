package org.example.java_labbwebshop.order.service;

import jakarta.transaction.Transactional;
import org.example.java_labbwebshop.cart.CartService;
import org.example.java_labbwebshop.cart.model.CartItem;
import org.example.java_labbwebshop.order.model.Order;
import org.example.java_labbwebshop.order.model.OrderItem;
import org.example.java_labbwebshop.order.repositories.OrderItemRepository;
import org.example.java_labbwebshop.order.repositories.OrderRepository;
import org.example.java_labbwebshop.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartService cartService;

    @Transactional
    public Order placeOrder(User user) {
        List<CartItem> cartItems = cartService.getCartItemsForUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty, cannot place order.");
        }

        Order order = new Order();
        order.setUser(user);
        order = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItemRepository.save(orderItem);
        }

        order.setOrderItems(orderItemRepository.findByOrder(order));

        orderRepository.save(order);
        cartService.clearCart(user);

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

