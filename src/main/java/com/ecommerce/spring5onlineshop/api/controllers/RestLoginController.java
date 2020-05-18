package com.ecommerce.spring5onlineshop.api.controllers;

import com.ecommerce.spring5onlineshop.api.model.UserDTO;
import com.ecommerce.spring5onlineshop.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class RestLoginController {

    private final UserService userService;

    public RestLoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserByUsername(@PathVariable String username) {
        return userService.findUserDTOByUsername(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createNewUser(@RequestBody UserDTO userDTO) {
        return userService.saveUserDTO(userDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        userDTO.setId(Long.valueOf(id));

        return userService.saveUserDTO(userDTO);
    }

    @PostMapping("/login/{username}/{password}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO loginUser(@PathVariable String username, @PathVariable String password) {
        log.debug("Username = " + username + ", password = " + password);

        return userService.loginUserByUsername(username, password);
    }

    @RequestMapping("/{id}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable String id) {

        userService.deleteUserById(Long.valueOf(id));
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}