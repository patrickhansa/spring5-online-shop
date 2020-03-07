package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {

    UserCommand saveUserCommand(UserCommand command);

    User findById(Long l);

    UserCommand findCommandByUsername(String username);

    Long findShoppingCartIdByUsername(String username);

    void setAuthority(UserCommand command, Authentication authentication);
}
