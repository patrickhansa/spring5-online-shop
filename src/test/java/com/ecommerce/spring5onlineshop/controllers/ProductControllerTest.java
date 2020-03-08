package com.ecommerce.spring5onlineshop.controllers;

import com.ecommerce.spring5onlineshop.commands.ProductCommand;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    @Mock
    ProductService productService;

    ProductController controller;

    @Mock
    Model model;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new ProductController(productService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetProduct() throws Exception {
        // Given
        Product product = new Product();
        product.setId(1L);

        // When
        when(productService.findById(anyLong())).thenReturn(product);

        // Then
        mockMvc.perform(get("/product/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/show"));
    }

    @Test
    public void testGetNewProductForm() throws Exception {
        mockMvc.perform(get("/product/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productForm"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        // Given
        ProductCommand command = new ProductCommand();
        command.setId(2L);

        // When
        when(productService.saveProductCommand(any())).thenReturn(command);

        // Then
        mockMvc.perform(post("/product")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/2/show"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        // Given
        ProductCommand command = new ProductCommand();
        command.setId(2L);

        // When
        when(productService.findCommandById(anyLong())).thenReturn(command);

        // Then
        mockMvc.perform(get("/product/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productForm"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/product/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(productService, times(1)).deleteById(anyLong());
    }

    @Test
    void showAllProducts() {
        // Given
        Set<Product> products = new HashSet<>();
        products.add(new Product());

        Product product = new Product();
        product.setId(1L);

        products.add(product);

        when(productService.getProducts()).thenReturn(products);

        ArgumentCaptor<Set<Product>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // When
        String viewName = controller.showAllProducts(model);

        // Then
        assertEquals("product/showCatalog", viewName);
        verify(productService, times(1)).getProducts();
        verify(model, times(1)).addAttribute(eq("products"), argumentCaptor.capture());
        Set<Product> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }
}
