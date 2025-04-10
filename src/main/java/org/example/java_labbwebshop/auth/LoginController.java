package org.example.java_labbwebshop.auth;

import org.example.java_labbwebshop.user.User;
import org.example.java_labbwebshop.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<User> user = userService.login(email, password);

        if (user.isEmpty()) {
            model.addAttribute("errorMessage", "Invalid email or password.");
            return "loginpage";
        }

        if (user.get().getRole() == User.Role.ADMIN) {
            return "redirect:/admin?userId=" + user.get().getId();
        }else{
            return "redirect:/home?userId=" + user.get().getId();
        }

    }

}
