package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    UserDetails loadUserByUsername(String username);

    User findUserByUsername(String username);

    UserCommand saveUserCommand(UserCommand command);

    User findById(Long l);

    UserCommand findCommandById(Long l);

    UserCommand findCommandByUsername(String username);

    void setAuthority(UserCommand command, Authentication authentication);
}
