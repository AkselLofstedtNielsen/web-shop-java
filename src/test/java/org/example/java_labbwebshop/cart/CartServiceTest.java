package org.example.java_labbwebshop.cart;

import org.example.java_labbwebshop.cart.model.Cart;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Cart cart;

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

        // Mock the cart behavior
        doNothing().when(cart).addProduct(testProduct);

        // Create a test cart item
        CartItem testCartItem = new CartItem(testProduct, 1);
        List<CartItem> testCartItems = new ArrayList<>();
        testCartItems.add(testCartItem);

        // Mock the getCartItems method
        when(cart.getCartItems()).thenReturn(testCartItems);

        cartService.addToCart(1L);
        List<CartItem> cartItems = cartService.getCartItems();

        assertEquals(1, cartItems.size());
        assertEquals("TestProduct", cartItems.get(0).getProduct().getName());
        assertEquals(1, cartItems.get(0).getQuantity());

        // Verify that addProduct was called
        verify(cart).addProduct(testProduct);
    }

    @Test
    public void testUpdateQuantity() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // Mock the cart behavior
        doNothing().when(cart).addProduct(testProduct);
        doNothing().when(cart).updateProduct(testProduct, 3);

        // Create a test cart item
        CartItem testCartItem = new CartItem(testProduct, 3);
        List<CartItem> testCartItems = new ArrayList<>();
        testCartItems.add(testCartItem);

        // Mock the getCartItems method
        when(cart.getCartItems()).thenReturn(testCartItems);

        cartService.addToCart(1L);
        cartService.updateQuantity(1L, 3);

        List<CartItem> cartItems = cartService.getCartItems();
        assertEquals(3, cartItems.get(0).getQuantity());

        // Verify that updateProduct was called
        verify(cart).updateProduct(testProduct, 3);
    }

    @Test
    public void testRemoveItem() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // Mock the cart behavior
        doNothing().when(cart).addProduct(testProduct);
        doNothing().when(cart).removeProduct(1L);

        // Mock the getCartItems method for empty cart after removal
        when(cart.getCartItems()).thenReturn(new ArrayList<>());

        cartService.addToCart(1L);
        cartService.removeItem(1L);

        List<CartItem> cartItems = cartService.getCartItems();
        assertTrue(cartItems.isEmpty());

        // Verify that removeProduct was called
        verify(cart).removeProduct(1L);
    }

    @Test
    public void testGetTotal() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));

        // Mock the cart behavior
        doNothing().when(cart).addProduct(testProduct);
        doNothing().when(cart).updateProduct(testProduct, 2);

        // Mock the getTotal method
        when(cart.getTotal()).thenReturn(BigDecimal.valueOf(24.0));

        cartService.addToCart(1L);
        cartService.updateQuantity(1L, 2);
        BigDecimal total = cartService.getTotal();

        assertEquals(BigDecimal.valueOf(24.00), total);


        // Verify that getTotal was called
        verify(cart).getTotal();
    }
}
