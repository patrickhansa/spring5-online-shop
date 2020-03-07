package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.converters.UserCommandToUser;
import com.ecommerce.spring5onlineshop.converters.UserToUserCommand;
import com.ecommerce.spring5onlineshop.model.User;
import com.ecommerce.spring5onlineshop.repositories.ShoppingCartRepository;
import com.ecommerce.spring5onlineshop.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    public static final String USERNAME = "Mike";

    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    ShoppingCartRepository shoppingCartRepository;

    @Mock
    UserCommandToUser userCommandToUser;

    @Mock
    UserToUserCommand userToUserCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userService = new UserServiceImpl(userRepository, shoppingCartRepository, userCommandToUser, userToUserCommand);
    }

    @Test
    void getUserByIdTest() {
        // Given
        User user = new User();
        user.setId(1L);
        Optional<User> userOptional = Optional.of(user);

        when(userRepository.findById(anyLong())).thenReturn(userOptional);

        // When
        User userReturned = userService.findById(1L);

        // Then
        assertNotNull(userReturned, "Null user returned");
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, never()).findAll();
    }

    @Test
    void getUserByUsernameTest() {
        // Given
        UserCommand userCommand = new UserCommand();
        userCommand.setUsername(USERNAME);

        when(userToUserCommand.convert(any())).thenReturn(userCommand);

        // When
        UserCommand userByUsername = userService.findCommandByUsername(USERNAME);

        // Then
        assertNotNull(userByUsername, "Null product returned");
        verify(userRepository, times(1)).getUserByUsername(anyString());
        verify(userRepository, never()).findAll();
    }
}
