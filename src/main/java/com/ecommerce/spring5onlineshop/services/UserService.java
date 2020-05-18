package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.api.model.UserDTO;
import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.model.User;
import org.springframework.security.core.Authentication;

public interface UserService {

    UserCommand saveUserCommand(UserCommand command);

    UserDTO saveUserDTO(UserDTO userDTO);

    User findById(Long l);

    UserCommand findCommandByUsername(String username);

    UserDTO findUserDTOByUsername(String username);

    UserDTO loginUserByUsername(String username, String password);

    Long findShoppingCartIdByUsername(String username);

    void setAuthority(UserCommand command, Authentication authentication);

    void deleteUserById(Long userId);
}
