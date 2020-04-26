package com.ecommerce.spring5onlineshop.api.controllers;

import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class RestLoginController {

    private final UserService userService;

    public RestLoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserCommand getUserByUsername(@PathVariable String username) {
        return userService.findCommandByUsername(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserCommand createNewUser(@RequestBody UserCommand userCommand) {
        return userService.saveUserCommand(userCommand);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public UserCommand updateUser(@PathVariable String id, @RequestBody UserCommand userCommand) {
        userCommand.setId(Long.valueOf(id));

        return userService.saveUserCommand(userCommand);
    }
}
