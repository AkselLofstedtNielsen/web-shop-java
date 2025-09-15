package org.example.java_labbwebshop.cart.model;

import lombok.Data;
import org.example.java_labbwebshop.apidiscog.model.DiscogsReleaseResponse;
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

    public void addRelease(DiscogsReleaseResponse release) {
        cartItems.stream()
                .filter(item -> item.getRelease().id() == release.id())
                .findFirst()
                .ifPresentOrElse(
                        item -> item.setQuantity(item.getQuantity() + 1),
                        () -> cartItems.add(new CartItem(release, 1))
                );
    }

    public void updateRelease(int releaseId, int quantity) {
        cartItems.removeIf(item -> item.getRelease().id() == releaseId);
        if (quantity > 0) {
            // Här behöver vi ha release‑objektet igen, men det sköts i CartService
        }
    }

    public void removeRelease(int releaseId) {
        cartItems.removeIf(item -> item.getRelease().id() == releaseId);
    }

    public BigDecimal getTotal() {
        return cartItems.stream()
                .map(item -> BigDecimal.valueOf( // Simulerat pris
                        100 + (item.getRelease().year() != null ? item.getRelease().year() % 50 : 0)
                ).multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear() {
        cartItems.clear();
    }
}
