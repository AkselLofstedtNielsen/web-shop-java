package org.example.java_labbwebshop.auth;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "loginpage";
    }

}
