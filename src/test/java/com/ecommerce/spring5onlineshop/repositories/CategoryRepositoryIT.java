package com.ecommerce.spring5onlineshop.repositories;

import com.ecommerce.spring5onlineshop.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CategoryRepositoryIT {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void findByDescriptionBook() {
        // When
        Optional<Category> categoryOptional = categoryRepository.findByDescription("Book");

        // Then
        assertEquals("Book", categoryOptional.get().getDescription());
    }

    @Test
    void findByDescriptionElectronics() {
        // When
        Optional<Category> categoryOptional = categoryRepository.findByDescription("Electronics");

        // Then
        assertEquals("Electronics", categoryOptional.get().getDescription());
    }

    @Test
    void findByDescriptionClothing() {
        // When
        Optional<Category> categoryOptional = categoryRepository.findByDescription("Clothing");

        // Then
        assertEquals("Clothing", categoryOptional.get().getDescription());
    }
}