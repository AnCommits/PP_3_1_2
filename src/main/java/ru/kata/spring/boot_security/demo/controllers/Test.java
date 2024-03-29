package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/guest")
public class Test {
    private final UserService userService;

    public Test(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("test")
    public String showAddUser(ModelMap model) {


        return "test";
    }

}
