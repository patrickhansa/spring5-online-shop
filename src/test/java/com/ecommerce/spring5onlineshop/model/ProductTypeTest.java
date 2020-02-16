package com.ecommerce.spring5onlineshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTypeTest {

    ProductType productType;

    @BeforeEach
    void setUp() {
        productType = new ProductType();
    }

    @Test
    void getDescription() {
        // Given
        String description = "foo";

        // When
        productType.setDescription(description);

        // Then
        assertEquals(description, productType.getDescription());
    }
}