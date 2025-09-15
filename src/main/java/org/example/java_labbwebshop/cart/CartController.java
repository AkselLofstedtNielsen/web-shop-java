package org.example.java_labbwebshop.cart;

import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.cart.model.CartItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart() {
        return ResponseEntity.ok(cartService.getCartItems());
    }

    @PostMapping("/add/{releaseId}")
    public ResponseEntity<Void> addToCart(@PathVariable int releaseId) {
        cartService.addReleaseToCart(releaseId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{releaseId}")
    public ResponseEntity<Void> updateQuantity(@PathVariable int releaseId, @RequestParam int quantity) {
        cartService.updateQuantity(releaseId, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{releaseId}")
    public ResponseEntity<Void> removeItem(@PathVariable int releaseId) {
        cartService.removeItem(releaseId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotal() {
        return ResponseEntity.ok(cartService.getTotal());
    }
}
