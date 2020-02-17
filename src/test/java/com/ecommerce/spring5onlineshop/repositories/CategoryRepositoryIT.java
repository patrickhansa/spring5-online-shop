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

        Optional<Category> categoryOptional = categoryRepository.findByDescription("Book");

        assertEquals("Book", categoryOptional.get().getDescription());
    }

    @Test
    void findByDescriptionFood() {

        Optional<Category> categoryOptional = categoryRepository.findByDescription("Food");

        assertEquals("Food", categoryOptional.get().getDescription());
    }

    @Test
    void findByDescriptionClothing() {

        Optional<Category> categoryOptional = categoryRepository.findByDescription("Clothing");

        assertEquals("Clothing", categoryOptional.get().getDescription());
    }
}