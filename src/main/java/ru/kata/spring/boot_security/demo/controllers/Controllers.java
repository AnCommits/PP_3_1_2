package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class Controllers {

    private final UserService userService;

    public Controllers(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test(ModelMap model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Set<Role> rs = user.getRoles();
        List<Role> roles = new ArrayList<>(rs);
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "test";
    }

    @PostMapping("/test2")
    public String test2(@ModelAttribute("user") User user) {

        System.out.println("------------------------------------ test2");
        return "redirect:/test";
    }
}
