package org.example.java_labbwebshop.auth;

import org.example.java_labbwebshop.user.SessionUser;
import org.example.java_labbwebshop.user.User;
import org.example.java_labbwebshop.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionUser sessionUser;

    @GetMapping("/login")
    public String getLoginPage() {
        return "loginpage";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {

        return userService.login(email, password)
                .map(user -> {
                    sessionUser.setUser(user);
                    String redirectUrl = user.getRole() == User.Role.ADMIN
                            ? "/admin"
                            : "/home";
                    return "redirect:" + redirectUrl;
                })
                .orElseGet(() -> {
                    model.addAttribute("errorMessage", "Invalid email or password.");
                    return "loginpage";
                });
    }
}
