package org.example.java_labbwebshop;

import org.example.java_labbwebshop.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    UserService userService;

    @GetMapping("/user")
    String getUserPage(Model model) {
        model.addAttribute("user", new User());
        return "loginpage";
    }
    @PostMapping
    String addUser(Model model,@ModelAttribute("wholeuser") User user) {
        userService.save(user);
        model.addAttribute("userlist", userService.findAll());
        model.addAttribute("user", new User());
        return "loginpage";
    }
}
