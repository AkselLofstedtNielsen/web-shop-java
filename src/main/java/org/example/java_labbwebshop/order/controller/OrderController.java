package org.example.java_labbwebshop.order.controller;

import org.example.java_labbwebshop.order.model.Order;
import org.example.java_labbwebshop.order.service.OrderService;
import org.example.java_labbwebshop.user.SessionUser;
import org.example.java_labbwebshop.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    SessionUser sessionUser;

    @PostMapping("/order/place")
    public String placeOrder() {
        User user = sessionUser.getUser();
        if (user == null) {
            return "redirect:/login?error=notfound";
        }
        try {
            Order order = orderService.placeOrder(user);
            return "redirect:/order/confirmation?orderId=" + order.getId();
        } catch (RuntimeException e) {
            return "redirect:/cart?error=" + e.getMessage();
        }
    }

    @GetMapping("/order/confirmation")
    public String orderConfirmation(@RequestParam("orderId") Long orderId, Model model) {
        try {
            Order order = orderService.getOrderById(orderId);
            BigDecimal totalPrice = orderService.calculateTotalPrice(order);
            model.addAttribute("order", order);
            model.addAttribute("totalPrice", totalPrice);
            return "orderconfirmation";
        } catch (RuntimeException e) {
            return "redirect:/cart?error=orderNotFound";
        }
    }

}
