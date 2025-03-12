package org.example.java_labbwebshop.controllers;

import jakarta.validation.Valid;
import org.example.java_labbwebshop.user.User;
import org.example.java_labbwebshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register"; // Om validering misslyckas, returnera till registreringssidan med felmeddelanden
        }

        userService.registerUser(user);
        return "redirect:/login"; // Omdirigera till inloggningssidan vid lyckad registrering
    }
}

