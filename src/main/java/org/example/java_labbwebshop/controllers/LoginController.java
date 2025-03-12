package org.example.java_labbwebshop.controllers;

import org.example.java_labbwebshop.user.User;
import org.example.java_labbwebshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "loginpage";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = userService.login(email, password);
        if (user.isPresent()) {
            return "redirect:/home"; // Skicka användaren till en dashboard-sida
        }
        return "redirect:/user?error"; // Skicka tillbaka till login om fel uppstår
    }
}
