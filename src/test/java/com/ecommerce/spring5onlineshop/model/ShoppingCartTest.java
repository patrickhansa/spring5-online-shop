package com.ecommerce.spring5onlineshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShoppingCartTest {

    ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart();
    }

    @Test
    void getQuantity() {
        // Given
        Integer quantity = 12;

        // When
        shoppingCart.setQuantity(quantity);

        // Then
        assertEquals(quantity, shoppingCart.getQuantity());
    }

    @Test
    void getProducts() {
        // Given
        String description = "foo";
        Product product1 = Product.builder().description(description).build();

        // When
        shoppingCart.setProducts(Set.of(product1));

        // Then
        assertEquals(description, shoppingCart.getProducts().iterator().next().getDescription());
        assertEquals(1, shoppingCart.getProducts().size());
    }
}