package org.example.java_labbwebshop.cart;

import lombok.AllArgsConstructor;
import org.example.java_labbwebshop.cart.model.CartItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart")
    public String showCart(Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        double total = cartService.getTotal();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        return "cart";
    }

    @PostMapping("/cart/show")
    public String showCartPost() {
        return "redirect:/cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId) {
        cartService.addToCart(productId);
        return "redirect:/home";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam("productId") Long productId,
                             @RequestParam int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeItem(@RequestParam("productId") Long productId) {
        cartService.removeItem(productId);
        return "redirect:/cart";
    }
}
