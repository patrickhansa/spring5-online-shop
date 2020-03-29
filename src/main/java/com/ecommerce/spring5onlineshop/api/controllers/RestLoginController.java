package com.ecommerce.spring5onlineshop.api.controllers;

import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.converters.UserToUserCommand;
import com.ecommerce.spring5onlineshop.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class RestLoginController {

    private final UserService userService;
    private final UserToUserCommand userToUserCommand;

    public RestLoginController(UserService userService, UserToUserCommand userToUserCommand) {
        this.userService = userService;
        this.userToUserCommand = userToUserCommand;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserCommand createNewUser(@RequestBody UserCommand userCommand) {
        return userService.saveUserCommand(userCommand);
    }

    @RequestMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserCommand getUserById(@PathVariable String id) {
        return userToUserCommand.convert(userService.findById(Long.valueOf(id)));
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public UserCommand updateUser(@PathVariable String id, @RequestBody UserCommand userCommand) {
        userCommand.setId(Long.valueOf(id));

        return userService.saveUserCommand(userCommand);
    }
}
