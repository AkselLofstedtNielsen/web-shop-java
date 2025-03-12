package org.example.java_labbwebshop.controllers;

import org.example.java_labbwebshop.user.User;
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

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService; // Antag att du har en UserService för att hämta inloggad användare

    @GetMapping("/cart")
    public String showCart(Model model) {
        User user = userService.getLoggedInUser(); // Hämta inloggad användare
        List<CartItem> cartItems = cartService.getCartItemsForUser(user);
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId) {
        User user = userService.getLoggedInUser(); // Hämta inloggad användare
        cartService.addToCart(user, productId);
        return "redirect:/cart";
    }
}

