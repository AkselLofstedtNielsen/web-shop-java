package org.example.java_labbwebshop.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.cart.CartService;
import org.example.java_labbwebshop.cart.model.CartItem;
import org.example.java_labbwebshop.emailservice.MailService;
import org.example.java_labbwebshop.order.OrderStatus;
import org.example.java_labbwebshop.order.model.Order;
import org.example.java_labbwebshop.order.model.OrderItem;
import org.example.java_labbwebshop.order.repositories.OrderRepository;
import org.example.java_labbwebshop.user.User;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final MailService mailService;

    @Transactional
    public Order placeOrder(User user) {
        List<CartItem> cartItems = cartService.getCartItems();

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    var release = cartItem.getRelease();
                    return OrderItem.builder()
                            .releaseId(release.id())
                            .title(release.title())
                            .artist(release.artist())
                            .coverImage(release.coverImage())
                            .price(calculatePrice(release)) // samma logik som i Cart
                            .quantity(cartItem.getQuantity())
                            .build();
                })
                .toList();

        Order order = Order.builder()
                .user(user)
                .orderItems(orderItems)
                .orderDate(java.time.LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .build();

        order.getOrderItems().forEach(item -> item.setOrder(order));

        order.setOrderNr(generateOrderNumber());

        orderRepository.save(order);

        mailService.sendOrderConfirmation(
                user.getEmail(),
                "Thank you for your order!",
                buildEmailContent(order)
        );

        cartService.clearCart();

        return order;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found: " + id));
    }

    public BigDecimal calculateTotalPrice(Order order) {
        return order.getOrderItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String generateOrderNumber() {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        long random = (long) (Math.random() * 1000);
        return "ORD-" + date + "-" + String.format("%03d", random);
    }

    private BigDecimal calculatePrice(org.example.java_labbwebshop.apidiscog.model.DiscogsReleaseResponse release) {
        // Simulerad prissättning
        return BigDecimal.valueOf(100 + (release.year() != null ? release.year() % 50 : 0));
    }

    private String buildEmailContent(Order order) {
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<h1>Thank you for your order!</h1>");
        emailContent.append("<p>Order number: ").append(order.getOrderNr()).append("</p>");
        emailContent.append("<table border='1' cellpadding='5' cellspacing='0'>");
        emailContent.append("<thead><tr>")
                .append("<th>Product</th>")
                .append("<th>Quantity</th>")
                .append("<th>Price</th>")
                .append("</tr></thead><tbody>");

        for (OrderItem item : order.getOrderItems()) {
            emailContent.append("<tr>")
                    .append("<td>").append(item.getArtist()).append(" - ").append(item.getTitle()).append("</td>")
                    .append("<td>").append(item.getQuantity()).append("</td>")
                    .append("<td>").append(item.getPrice()).append(" EUR</td>")
                    .append("</tr>");
        }

        emailContent.append("</tbody></table>");
        emailContent.append("<h3>Total: ").append(calculateTotalPrice(order)).append(" EUR</h3>");
        return emailContent.toString();
    }
}
