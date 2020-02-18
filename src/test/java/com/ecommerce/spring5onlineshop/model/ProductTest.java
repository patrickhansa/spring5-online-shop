package com.ecommerce.spring5onlineshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

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
        Float price = 1.1F;

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
        product.setStock(stock);

        // Then
        assertEquals(stock, product.getStock());
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
        ShoppingCart shoppingCart = ShoppingCart.builder().member(member).build();

        // When
        product.setShoppingCart(shoppingCart);

        // Then
        assertEquals(lastName, product.getShoppingCart().getMember().getLastName());
    }

    @Test
    void getCategories() {
        // Given
        String categoryDescription = "foo";
        Category category = Category.builder().description(categoryDescription).build();

        // When
        product.setCategories(Set.of(category));

        // Then
        assertEquals(categoryDescription, product.getCategories().iterator().next().getDescription());
        assertEquals(1, product.getCategories().size());
    }
}