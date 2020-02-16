package com.ecommerce.spring5onlineshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
    }

    @Test
    void getName() {
        // Given
        String name = "foo";

        // When
        product.setName(name);

        // Then
        assertEquals(name, product.getName());
    }

    @Test
    void getPrice() {
        // Given
        Integer price = 100;

        // When
        product.setPrice(price);

        // Then
        assertEquals(price, product.getPrice());
    }

    @Test
    void getStock() {
        // Given
        Integer stock = 2;

        // When
        product.setPrice(stock);

        // Then
        assertEquals(stock, product.getPrice());
    }

    @Test
    void getDescription() {
        // Given
        String description = "foo";

        // When
        product.setDescription(description);

        // Then
        assertEquals(description, product.getDescription());
    }

    @Test
    void getCart() {
        // Given
        String lastName = "Smith";
        Member member = Member.builder().lastName(lastName).build();
        Cart cart = Cart.builder().member(member).build();

        // When
        product.setCart(cart);

        // Then
        assertEquals(lastName, product.getCart().getMember().getLastName());
    }
}