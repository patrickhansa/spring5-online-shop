package com.ecommerce.spring5onlineshop.api.controllers;

import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.converters.UserToUserCommand;
import com.ecommerce.spring5onlineshop.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestLoginControllerTest {

    @Mock
    UserService userService;

    @Mock
    UserToUserCommand userToUserCommand;

    @InjectMocks
    RestLoginController restLoginController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(restLoginController).build();
    }

    @Test
    void createNewUser() throws Exception {
        // Given
        UserCommand userCommand = new UserCommand();
        given(userService.saveUserCommand(userCommand)).willReturn(userCommand);

        // Then
        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userCommand)))
                        .andExpect(status().isCreated());
    }

    @Test
    void getUserById() throws Exception {
        mockMvc.perform(get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception {
        // Given
        UserCommand userCommand = new UserCommand();
        given(userService.saveUserCommand(userCommand)).willReturn(userCommand);

        // Then
        mockMvc.perform(put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userCommand)))
                .andExpect(status().isOk());
    }
}