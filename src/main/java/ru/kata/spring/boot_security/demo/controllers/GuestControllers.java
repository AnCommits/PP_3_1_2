package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/guest")
public class GuestControllers {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    private User userToRepeatEdit;
    private boolean emailError;

    public GuestControllers(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("sign-up")
    public String showAddUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Регистрация пользователя");
        model.addAttribute("title2", "Новый пользователь");
        return "user-edit";
    }

    @GetMapping("/show-repeat-add-user")
    public String showRepeatAddUser(ModelMap model) {
        model.addAttribute("user", userToRepeatEdit);
        model.addAttribute("email_err", emailError);
        model.addAttribute("title", "Регистрация пользователя");
        model.addAttribute("title2", "Новый пользователь");
        return "user-edit";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user) {
        String emailFromForm = user.getEmail();
        User userFromDb = userService.getUserByEmail(emailFromForm);
        emailError = userFromDb != null;
        if (emailError) {
            userToRepeatEdit = user;
            return "redirect:/guest/show-repeat-add-user";
        }
        user.setRoles(Role.getSetOfRoles(1));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/";
    }
}
