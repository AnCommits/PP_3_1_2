package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.helper.RolesForView;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserControllers {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    private User userToRepeatEdit;
    private List<Role> roles;
    private boolean emailError;

    public UserControllers(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping
    public String showUser(ModelMap model, Authentication authentication) {
        long id = ((User) authentication.getPrincipal()).getId();
        // After user changes his/her email the email in authentication
        // is not the same as the email in DB
        User user = userService.getUserById(id);
        user.setRolesForViews(new RolesForView(user.getRolesNames()));
        model.addAttribute("title", "Моя страница");
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/show-edit-user")
    public String showEditUser(@RequestParam long id, ModelMap model) {
        User user = userService.getUserById(id);
        roles = user.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("title", "Моя страница");
        model.addAttribute("title2", "Редактирование моих данных");
        return "user-edit";
    }

    @GetMapping("/show-repeat-edit-user")
    public String showRepeatEditUser(ModelMap model) {
        model.addAttribute("user", userToRepeatEdit);
        model.addAttribute("email_err", emailError);
        model.addAttribute("title", "Моя страница");
        model.addAttribute("title2", "Редактирование моих данных");
        return "user-edit";
    }

    @PutMapping("/save-user")
    public String updateUser(@ModelAttribute("user") User user, Principal principal) {
        long idFromForm = user.getId();
        String emailFromForm = user.getEmail();
        User userFromDb = userService.getUserByEmail(emailFromForm);
        emailError = userFromDb != null && idFromForm != userFromDb.getId();
        if (emailError) {
            userToRepeatEdit = user;
            return "redirect:/user/show-repeat-edit-user";
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(user);
        return "redirect:/user";
    }

    @DeleteMapping("/remove-user/{id}")
    public String removeUser(@PathVariable long id) {
        userService.removeUserById(id);
        userToRepeatEdit = null;
        return "redirect:/logout";
    }
}
