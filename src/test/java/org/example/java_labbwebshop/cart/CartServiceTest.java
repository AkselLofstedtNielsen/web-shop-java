package org.example.java_labbwebshop.cart;

import org.example.java_labbwebshop.cart.model.CartItem;
import org.example.java_labbwebshop.product.Product;
import org.example.java_labbwebshop.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    private Product testProduct;

    @BeforeEach
    public void setUp() {
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("TestProduct");
        testProduct.setPrice(new BigDecimal("12.0"));
    }

    @Test
    public void testAddToCart() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        cartService.addToCart(1L);
        List<CartItem> cartItems = cartService.getCartItems();

        assertEquals(1, cartItems.size());
        assertEquals("TestProduct", cartItems.get(0).getProduct().getName());
        assertEquals(1, cartItems.get(0).getQuantity());
    }

    @Test
    public void testUpdateQuantity() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        cartService.addToCart(1L);
        cartService.updateQuantity(1L, 3);

        List<CartItem> cartItems = cartService.getCartItems();
        assertEquals(3, cartItems.get(0).getQuantity());
    }

    @Test
    public void testRemoveItem() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        cartService.addToCart(1L);
        cartService.removeItem(1L);

        List<CartItem> cartItems = cartService.getCartItems();
        assertTrue(cartItems.isEmpty());
    }

    @Test
    public void testGetTotal() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        cartService.addToCart(1L);
        cartService.updateQuantity(1L, 2);
        double total = cartService.getTotal();

        assertEquals(24.0, total);
    }
}
