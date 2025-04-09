package org.example.java_labbwebshop.cart;

import org.example.java_labbwebshop.cart.model.Cart;
import org.example.java_labbwebshop.cart.model.CartItem;
import org.example.java_labbwebshop.cart.repositories.CartItemRepository;
import org.example.java_labbwebshop.cart.repositories.CartRepository;
import org.example.java_labbwebshop.category.Category;
import org.example.java_labbwebshop.product.Product;
import org.example.java_labbwebshop.product.ProductRepository;
import org.example.java_labbwebshop.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {
    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;




    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addToCart() {
        Cart cart = new Cart();

        User user = new User();

        Product product = new Product();

        cart.setCartItems(new ArrayList<CartItem>());

        user.setId(1L);
        cart.setUser(user);

        product.setId(1L);
        product.setPrice(BigDecimal.valueOf(100.0));


    }

    @Test
    void removeItem() {
    }

    @Test
    void getTotal() {
    }
}