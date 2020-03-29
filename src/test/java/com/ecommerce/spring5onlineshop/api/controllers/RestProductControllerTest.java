package com.ecommerce.spring5onlineshop.api.controllers;

import com.ecommerce.spring5onlineshop.api.mapper.ProductMapper;
import com.ecommerce.spring5onlineshop.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestProductControllerTest {

    @Mock
    ProductService productService;

    @Mock
    ProductMapper productMapper;

    @InjectMocks
    RestProductController restProductController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(restProductController).build();
    }

    @Test
    void getAllProducts() throws Exception {
        mockMvc.perform(get("/api/product/showAll")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    void getProductById() throws Exception {
        mockMvc.perform(get("/api/product/1/show")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}