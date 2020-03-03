package com.ecommerce.spring5onlineshop.controllers;

import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.model.User;
import com.ecommerce.spring5onlineshop.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class LoginControllerTest {

    LoginController loginController;

    @Mock
    UserService userService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        loginController = new LoginController(userService);

        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void getLoginPage() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/loginForm"));
    }

    @Test
    public void testGetRegistrationForm() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/registration"));
    }

    @Test
    public void testPostRegistrationForm() throws Exception {
        mockMvc.perform(post("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/loginForm"));
    }

    @Test
    public void testPostUpdateUserForm() throws Exception {
        // Given
        UserCommand command = new UserCommand();
        command.setId(2L);

        // When
        when(userService.saveUserCommand(any())).thenReturn(command);

        // Then
        mockMvc.perform(post("/user"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/2/show"));
    }

    @Test
    public void testGetUser() throws Exception {
        // Given
        User user = new User();
        user.setId(1L);

        // When
        when(userService.findById(anyLong())).thenReturn(user);

        // Then
        mockMvc.perform(get("/user/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/show"));
    }
}