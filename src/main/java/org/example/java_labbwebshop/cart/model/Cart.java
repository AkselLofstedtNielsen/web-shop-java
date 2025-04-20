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

    private List<CartItem> cartItems = new ArrayList<>();

    public void addProduct(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        cartItems.add(new CartItem(product, 1));
    }

    public void updateProduct(Product product, int quantity) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(product.getId()));
        if (quantity > 0) {
            cartItems.add(new CartItem(product, quantity));
        }
    }

    public void removeProduct(Long productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public double getTotal() {
        return cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).doubleValue())
                .sum();
    }

    public void clear() {
        cartItems.clear();
    }
}
