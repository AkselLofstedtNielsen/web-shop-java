package org.example.java_labbwebshop.controllers;

import org.example.java_labbwebshop.User.User;
import org.example.java_labbwebshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @Autowired
    UserService userService;

    @GetMapping("/register")
    String getRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    String registerUser(@ModelAttribute("user") User user) {
        userService.registerUser(user);
        return "redirect:/login"; // Skicka användaren till login efter registrering
    }
}

