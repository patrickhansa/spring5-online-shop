package com.ecommerce.spring5onlineshop.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class LoginControllerTest {

    LoginController loginController;

    @BeforeEach
    void setUp() {

        loginController = new LoginController();
    }

    @Test
    void getLoginPage() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();

        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginForm"));
    }
}