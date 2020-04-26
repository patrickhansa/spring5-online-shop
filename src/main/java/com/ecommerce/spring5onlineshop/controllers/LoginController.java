package com.ecommerce.spring5onlineshop.controllers;

import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping("user/edit")
    public String editUser(Authentication authentication, Model model) {

        UserDetails user = (UserDetails) authentication.getPrincipal();

        model.addAttribute("user", userService.findCommandByUsername(user.getUsername()));

        return "login/editUserForm";
    }

    @PostMapping("/user")
    public String updateUser(@ModelAttribute UserCommand command, Authentication authentication, Model model) {

        userService.setAuthority(command, authentication);
        UserCommand savedCommand = userService.saveUserCommand(command);

        model.addAttribute("user", savedCommand);

        return "redirect:/user/" + savedCommand.getId() + "/show";
    }

    @RequestMapping("/user/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("user", userService.findById(Long.valueOf(id)));

        return "login/show";
    }

    @RequestMapping("/user/{id}/delete")
    public String deleteUserById(@PathVariable String id) {

        userService.deleteUserById(Long.valueOf(id));
        SecurityContextHolder.getContext().setAuthentication(null);

        return "index";
    }
}
