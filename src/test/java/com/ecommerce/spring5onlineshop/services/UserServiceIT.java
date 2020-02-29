package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.model.User;
import com.ecommerce.spring5onlineshop.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceIT {

    public static final String USERNAME = "Jane";
    public static final String PASSWORD = "sdfewrwe";
    public static final String NOT_FOUND = "n/a";

    UserServiceImpl userServiceImpl;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userServiceImpl = new UserServiceImpl(userRepository);
    }

    @Test
    void loadUserByUsername() {
        // Given
        User user = User.builder().username(USERNAME).password(PASSWORD).build();
        userRepository.save(user);

        // When
        UserDetails userDetails = userServiceImpl.loadUserByUsername(USERNAME);

        // Then
        assertEquals(USERNAME, userDetails.getUsername());
        assertEquals(PASSWORD, userDetails.getPassword());
    }

    @Test
    void whenUsernameNotFoundExceptionThrown() {
        // Given
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userServiceImpl.loadUserByUsername(NOT_FOUND);
        });

        // When
        String expectedMessage = "User '" + NOT_FOUND + "' not found";
        String actualMessage = exception.getMessage();

        // Then
        assertTrue(actualMessage.contains(expectedMessage));
    }
}