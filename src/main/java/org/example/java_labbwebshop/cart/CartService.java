package org.example.java_labbwebshop.cart;

import lombok.AllArgsConstructor;
import org.example.java_labbwebshop.cart.model.Cart;
import org.example.java_labbwebshop.cart.model.CartItem;
import org.example.java_labbwebshop.exception.ProductNotFoundException;
import org.example.java_labbwebshop.product.Product;
import org.example.java_labbwebshop.product.ProductRepository;
import org.example.java_labbwebshop.api.service.DiscogsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class CartService {

    private final Cart cart;
    private final ProductRepository productRepository;
    private final DiscogsService discogsService;

    public void addToCart(Long productId) {
        cart.addProduct(findProductById(productId));
    }

    public void addReleaseToCart(int releaseId) {
        Product product = discogsService.saveReleaseAsProduct(releaseId);
        cart.addProduct(product);
    }

    public void updateQuantity(Long productId, int quantity) {
        cart.updateProduct(findProductById(productId), quantity);
    }

    public void removeItem(Long productId) {
        cart.removeProduct(productId);
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

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }
}
