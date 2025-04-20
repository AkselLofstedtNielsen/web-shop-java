package org.example.java_labbwebshop.cart;

import lombok.AllArgsConstructor;
import org.example.java_labbwebshop.cart.model.Cart;
import org.example.java_labbwebshop.cart.model.CartItem;
import org.example.java_labbwebshop.product.Product;
import org.example.java_labbwebshop.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CartService {

    private final Cart cart;
    private final ProductRepository productRepository;

    public void addToCart(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        cart.addProduct(product);
    }

    public void updateQuantity(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        cart.updateProduct(product, quantity);
    }

    public void removeItem(Long productId) {
        cart.removeProduct(productId);
    }

    public void clearCart() {
        cart.clear();
    }

    public double getTotal() {
        return cart.getTotal();
    }

    public List<CartItem> getCartItems() {
        return cart.getCartItems();
    }
}

