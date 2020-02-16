package com.ecommerce.spring5onlineshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartTest {

    Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart();
    }

    @Test
    void getQuantity() {
        // Given
        Integer quantity = 12;

        // When
        cart.setQuantity(quantity);

        // Then
        assertEquals(quantity, cart.getQuantity());
    }

    @Test
    void getMember() {
        // Given
        String firstName = "Mike";
        Member member = new Member();
        member.setFirstName(firstName);

        // When
        cart.setMember(member);

        // Then
        assertEquals(firstName, cart.getMember().getFirstName());
    }

    @Test
    void getProducts() {
        // Given
        String description = "foo";
        Product product1 = Product.builder().description(description).build();

        // When
        cart.setProducts(Set.of(product1));

        // Then
        assertEquals(description, cart.getProducts().iterator().next().getDescription());
        assertEquals(1, cart.getProducts().size());
    }
}