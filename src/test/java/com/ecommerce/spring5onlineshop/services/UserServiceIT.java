package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.converters.UserCommandToUser;
import com.ecommerce.spring5onlineshop.converters.UserToUserCommand;
import com.ecommerce.spring5onlineshop.model.User;
import com.ecommerce.spring5onlineshop.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceIT {

    public static final String USERNAME = "Jane";
    public static final String PASSWORD = "sdfewrwe";
    public static final String NOT_FOUND = "n/a";
    public static final String NEW_USERNAME = "new_user";

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    UserCommandToUser userCommandToUser;

    @Autowired
    UserToUserCommand userToUserCommand;

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

    @Transactional
    @Test
    void testSaveOfUsername() {
        // Given
        Iterable<User> users = userRepository.findAll();
        User testUser = users.iterator().next();
        UserCommand testUserCommand = userToUserCommand.convert(testUser);

        // When
        testUserCommand.setUsername(NEW_USERNAME);
        UserCommand savedProductCommand = userService.saveUserCommand(testUserCommand);

        // Then
        assertEquals(NEW_USERNAME, savedProductCommand.getUsername());
        assertEquals(testUser.getId(), savedProductCommand.getId());
        assertEquals(testUser.getPassword(), savedProductCommand.getPassword());
        assertEquals(testUser.getFirstName(), savedProductCommand.getFirstName());
        assertEquals(testUser.getLastName(), savedProductCommand.getLastName());
        assertEquals(testUser.getAddress(), savedProductCommand.getAddress());
        assertEquals(testUser.getBirthDate(), savedProductCommand.getBirthDate());
        assertEquals(testUser.getEmail(), savedProductCommand.getEmail());
        assertEquals(testUser.getGender(), savedProductCommand.getGender());
        assertEquals(testUser.getPhone(), savedProductCommand.getPhone());
        assertEquals(testUser.getAuthorities().size(), savedProductCommand.getAuthorities().size());
    }
}