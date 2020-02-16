package com.ecommerce.spring5onlineshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseEntityTest {

    BaseEntity baseEntity;

    @BeforeEach
    void setUp() {
        baseEntity = new BaseEntity();
    }

    @Test
    void getId() {
        // Given
        Long id = 2L;

        // When
        baseEntity.setId(id);

        // Then
        assertEquals(id, baseEntity.getId());
    }
}