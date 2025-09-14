package org.example.java_labbwebshop.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.user.UserService;
import org.example.java_labbwebshop.user.dto.CreateOrUpdateUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class RegisterController {

    private final UserService userService;

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new CreateOrUpdateUserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") CreateOrUpdateUserDto userDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }

        if (userService.emailExists(userDto.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Email is already in use.");
            model.addAttribute("user", userDto);
            return "register";
        }

        userService.create(userDto);
        return "redirect:/login";
    }

}
