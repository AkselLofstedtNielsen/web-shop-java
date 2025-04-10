package org.example.java_labbwebshop.cart;

import org.example.java_labbwebshop.cart.model.Cart;
import org.example.java_labbwebshop.cart.model.CartItem;
import org.example.java_labbwebshop.cart.repositories.CartItemRepository;
import org.example.java_labbwebshop.cart.repositories.CartRepository;
import org.example.java_labbwebshop.product.Product;
import org.example.java_labbwebshop.product.ProductRepository;
import org.example.java_labbwebshop.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    private User testUser;
    private Product testProduct;
    private Cart testCart;

    @BeforeEach
    public void setUp(){
        //MockitoAnnotations.initMocks(this); Onödig med JUnit5 extension, @ExtendWith(MockitoExtension.class) = skapar upp mock obj

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@tester.com");

        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("TestProduct");
        testProduct.setPrice(new BigDecimal("12.0"));

        testCart = new Cart();
        testCart.setId(1L);
        testCart.setUser(testUser);
        testCart.setCartItems(new ArrayList<>());

        CartItem testCartItem = new CartItem();
        testCartItem.setId(1L);
        testCartItem.setCart(testCart);
        testCartItem.setProduct(testProduct);
        testCartItem.setQuantity(2);

    }

    @Test
    public void testGetCartForUser(){
        when(cartRepository.findByUser(testUser)).thenReturn(Optional.of(testCart));

        Cart result = cartService.getCartForUser(testUser);

        assertNotNull(result);
        assertEquals(testCart, result);
        verify(cartRepository).findByUser(testUser);
        verify(cartRepository, never()).save(any(Cart.class));
    }

    @Test
    public void testAddCartItem(){
        Long productId = 1L;
        when(cartRepository.findByUser(testUser)).thenReturn(Optional.of(testCart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));

        cartService.addToCart(testUser, productId);

        verify(cartItemRepository).save(argThat(item ->
                item.getCart().equals(testCart) &&
                        item.getProduct().equals(testProduct) &&
                        item.getQuantity() == 1
        ));
    }



}
