package org.example.java_labbwebshop.service;

import jakarta.transaction.Transactional;
import org.example.java_labbwebshop.model.Product;
import org.example.java_labbwebshop.User.User;
import org.example.java_labbwebshop.model.cart.Cart;
import org.example.java_labbwebshop.model.cart.CartItem;
import org.example.java_labbwebshop.repositories.CartItemRepository;
import org.example.java_labbwebshop.repositories.CartRepository;
import org.example.java_labbwebshop.repositories.ProductRepository;
import org.example.java_labbwebshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

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

    public List<CartItem> getCartItemsForUser(User user) {
        return getCartForUser(user).getCartItems();
    }
}

