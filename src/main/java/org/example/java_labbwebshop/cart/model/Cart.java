package org.example.java_labbwebshop.cart.model;

import lombok.Data;
import org.example.java_labbwebshop.product.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@SessionScope
public class Cart {

    private final List<CartItem> cartItems = new ArrayList<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + 1),
                        () -> cartItems.add(new CartItem(product, 1))
                );
    }

    public void updateProduct(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        cartItems.removeIf(item -> item.getProduct().getId().equals(product.getId()));
        if (quantity > 0) {
            cartItems.add(new CartItem(product, quantity));
        }
    }

    public void removeProduct(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public BigDecimal getTotal() {
        return cartItems.stream()
                .map(item -> item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear() {
        cartItems.clear();
    }
}
