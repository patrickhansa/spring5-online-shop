package com.ecommerce.spring5onlineshop.controllers;

import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String login() {

        return "login/loginForm";
    }

    @RequestMapping("/registration")
    public String registration(Model model) {

        UserCommand user = new UserCommand();

        model.addAttribute("user", user);

        return "login/registration";
    }

    @PostMapping("/registration")
    public String createNewUser(@ModelAttribute UserCommand command, Model model) {

        UserCommand savedCommand = userService.saveUserCommand(command);

        model.addAttribute("user", savedCommand);

        return "login/loginForm";
    }
}
