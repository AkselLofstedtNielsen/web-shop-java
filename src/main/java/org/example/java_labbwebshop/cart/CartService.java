package org.example.java_labbwebshop.cart;

import jakarta.transaction.Transactional;
import org.example.java_labbwebshop.cart.model.Cart;
import org.example.java_labbwebshop.cart.model.CartItem;
import org.example.java_labbwebshop.cart.repositories.CartItemRepository;
import org.example.java_labbwebshop.cart.repositories.CartRepository;
import org.example.java_labbwebshop.product.Product;
import org.example.java_labbwebshop.user.User;
import org.example.java_labbwebshop.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getCartForUser(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    @Transactional
    public void addToCart(User user, Long productId) {
        Cart cart = getCartForUser(user);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    return newItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void updateQuantity(User user, Long productId, int quantity) {
        Cart cart = getCartForUser(user);
        cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    if (quantity > 0) {
                        item.setQuantity(quantity);
                        cartItemRepository.save(item);
                    } else {
                        cart.getCartItems().remove(item);
                        cartItemRepository.delete(item);
                    }
                });
    }

    @Transactional
    public void removeItem(User user, Long productId) {
        Cart cart = getCartForUser(user);
        cart.getCartItems().removeIf(item -> {
            boolean match = item.getProduct().getId().equals(productId);
            if (match) cartItemRepository.delete(item);
            return match;
        });
    }

    public double getTotal(User user) {
        Cart cart = getCartForUser(user);
        return cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).doubleValue())
                .sum();
    }


    public List<CartItem> getCartItemsForUser(User user) {
        return getCartForUser(user).getCartItems();
    }
}

