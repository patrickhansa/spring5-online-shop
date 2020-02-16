package com.ecommerce.spring5onlineshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void getDescription() {
        // Given
        String description = "foo";

        // When
        category.setDescription(description);

        // Then
        assertEquals(description, category.getDescription());
    }
}