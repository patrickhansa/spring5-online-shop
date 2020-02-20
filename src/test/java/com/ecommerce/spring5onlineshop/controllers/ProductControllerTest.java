package com.ecommerce.spring5onlineshop.controllers;

import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ProductControllerTest {

    @Mock
    ProductService productService;

    ProductController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new ProductController(productService);
    }

    @Test
    public void testGetProduct() throws Exception {
        // Given
        Product product = new Product();
        product.setId(1L);

        when(productService.findById(anyLong())).thenReturn(product);

        // When
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Then
        mockMvc.perform(get("/product/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/show"));
    }
}
