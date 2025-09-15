package org.example.java_labbwebshop.cart;

import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.apidiscog.model.DiscogsReleaseResponse;
import org.example.java_labbwebshop.apidiscog.service.DiscogsService;
import org.example.java_labbwebshop.cart.model.Cart;
import org.example.java_labbwebshop.cart.model.CartItem;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final Cart cart;
    private final DiscogsService discogsService;

    public void addReleaseToCart(int releaseId) {
        DiscogsReleaseResponse release = discogsService.getReleaseById(releaseId);
        cart.addRelease(release);
    }

    public void updateQuantity(int releaseId, int quantity) {
        if (quantity > 0) {
            DiscogsReleaseResponse release = discogsService.getReleaseById(releaseId);
            cart.removeRelease(releaseId);
            cart.addRelease(release);
        } else {
            cart.removeRelease(releaseId);
        }
    }

    public void removeItem(int releaseId) {
        cart.removeRelease(releaseId);
    }

    public void clearCart() {
        cart.clear();
    }

    public BigDecimal getTotal() {
        return cart.getTotal();
    }

    public List<CartItem> getCartItems() {
        return cart.getCartItems();
    }
}
