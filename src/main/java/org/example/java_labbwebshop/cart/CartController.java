package org.example.java_labbwebshop.cart;

import lombok.AllArgsConstructor;
import org.example.java_labbwebshop.cart.model.CartItem;
import org.example.java_labbwebshop.user.SessionUser;
import org.example.java_labbwebshop.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
public class CartController {

    private CartService cartService;

    private SessionUser sessionUser;

    @GetMapping("/cart")
    public String showCart(Model model) {
        User user = sessionUser.getUser();
        if (user == null) {
            return "redirect:/login?error=notfound";
        }

        List<CartItem> cartItems = cartService.getCartItemsForUser(user);
        double total = cartService.getTotal(user);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        return "cart";
    }

    @PostMapping("/cart/show")
    public String showCartPost() {
        if (sessionUser.getUser() == null) {
            return "redirect:/login?error=notfound";
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId) {
        User user = sessionUser.getUser();
        if (user == null) {
            return "redirect:/login?error=notfound";
        }

        cartService.addToCart(user, productId);
        return "redirect:/home";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam("productId") Long productId,
                             @RequestParam int quantity) {
        User user = sessionUser.getUser();
        if (user == null) {
            return "redirect:/login?error=notfound";
        }
        cartService.updateQuantity(user, productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeItem(@RequestParam("productId") Long productId) {
        User user = sessionUser.getUser();
        if (user == null) {
            return "redirect:/login?error=notfound";
        }
        cartService.removeItem(user, productId);
        return "redirect:/cart";
    }
}
