package org.example.java_labbwebshop.controllers;

import org.example.java_labbwebshop.model.User;
import org.example.java_labbwebshop.model.cart.CartItem;
import org.example.java_labbwebshop.service.CartService;
import org.example.java_labbwebshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/cart")
    public String showCart(@RequestParam("userId") Long userId, Model model) {
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            return "redirect:/login?error=notfound";
        }
        List<CartItem> cartItems = cartService.getCartItemsForUser(user.get());
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("userId", userId);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {
        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            return "redirect:/login?error=notfound";
        }
        cartService.addToCart(user.get(), productId);
        return "redirect:/cart?userId=" + userId;
    }

}

