package com.ecommerce.spring5onlineshop.api.controllers;

import com.ecommerce.spring5onlineshop.api.mapper.ProductMapper;
import com.ecommerce.spring5onlineshop.services.ShoppingCartService;
import com.ecommerce.spring5onlineshop.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestShoppingCartControllerTest {

    @Mock
    UserService userService;

    @Mock
    ShoppingCartService shoppingCartService;

    @Mock
    ProductMapper productMapper;

    @InjectMocks
    RestShoppingCartController restShoppingCartController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(restShoppingCartController).build();
    }

    @Test
    void getProductsInShoppingCart() throws Exception {
        mockMvc.perform(get("/api/shoppingCart/username")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        then(shoppingCartService).should().getProductsInShoppingCart(anyLong());
        then(userService).should().findShoppingCartIdByUsername("username");
    }

    @Test
    void placeOrder() throws Exception {
        mockMvc.perform(get("/api/shoppingCart/username/purchase"))
                        .andExpect(status().isOk());

        then(shoppingCartService).should().placeOrder(anyLong());
        then(userService).should().findShoppingCartIdByUsername("username");
    }

    @Test
    void addProductToShoppingCart() throws Exception {
        mockMvc.perform(put("/api/shoppingCart/username/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());

        then(shoppingCartService).should().saveShoppingCartCommand(any());
        then(shoppingCartService).should().addProductToShoppingCart(eq(1L), anyLong());
        then(userService).should().findShoppingCartIdByUsername("username");
    }

    @Test
    void deleteProductInShoppingCart() throws Exception {
        mockMvc.perform(delete("/api/shoppingCart/username/1"))
                        .andExpect(status().isOk());

        then(userService).should().findShoppingCartIdByUsername("username");
        then(shoppingCartService).should().removeItemFromShoppingCart(anyLong(), eq(1L));
    }
}