package org.example.java_labbwebshop.order.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.java_labbwebshop.cart.CartService;
import org.example.java_labbwebshop.cart.model.CartItem;
import org.example.java_labbwebshop.emailservice.MailService;
import org.example.java_labbwebshop.order.model.Order;
import org.example.java_labbwebshop.order.model.OrderItem;
import org.example.java_labbwebshop.order.repositories.OrderRepository;
import org.example.java_labbwebshop.user.User;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final MailService mailService;


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

        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // t.ex. 20250420
        long random = (long) (Math.random() * 1000); // enkel pseudo-unik suffix
        order.setOrderNr("ORD-" + date + "-" + String.format("%03d", random));

        // Spara ordern och alla items
        orderRepository.save(order);

        // Email-bekräftelse
        StringBuilder emailContent = new StringBuilder();

        emailContent.append("<h1>Tack för din beställning!</h1>");
        emailContent.append("<p>Ordernummer: ").append(order.getOrderNr()).append("</p>");
        emailContent.append("<table border='1' cellpadding='5' cellspacing='0'>");
        emailContent.append("<thead><tr>")
                .append("<th>Produkt</th>")
                .append("<th>Antal</th>")
                .append("<th>Pris</th>")
                .append("</tr></thead>");
        emailContent.append("<tbody>");

        for (OrderItem item : order.getOrderItems()) {
            emailContent.append("<tr>")
                    .append("<td>").append(item.getProduct().getName()).append("</td>")
                    .append("<td>").append(item.getQuantity()).append("</td>")
                    .append("<td>").append(item.getProduct().getPrice()).append(" kr</td>")
                    .append("</tr>");
        }

        emailContent.append("</tbody></table>");

        BigDecimal totalPrice = calculateTotalPrice(order);
        emailContent.append("<h3>Totalt: ").append(totalPrice).append(" kr</h3>");


        mailService.sendOrderConfirmation(
                user.getEmail(),
                "Tack för din beställning!",
                emailContent.toString()
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
                .map(orderItem -> orderItem.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
