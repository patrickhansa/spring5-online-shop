package com.ecommerce.spring5onlineshop.repositories;

import com.ecommerce.spring5onlineshop.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CategoryRepositoryIT {

    private static final String BOOK_CATEGORY_DESCRIPTION = "Book";
    private static final String ELECTRONICS_CATEGORY_DESCRIPTION = "Electronics";
    private static final String CLOTHING_CATEGORY_DESCRIPTION = "Clothing";

    @Autowired
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        Category bookCategory = Category.builder().description(BOOK_CATEGORY_DESCRIPTION).build();
        Category electronicsCategory = Category.builder().description(ELECTRONICS_CATEGORY_DESCRIPTION).build();
        Category clothingCategory = Category.builder().description(CLOTHING_CATEGORY_DESCRIPTION).build();

        categoryRepository.saveAll(Set.of(bookCategory, electronicsCategory, clothingCategory));
    }

    @Test
    void findByDescriptionBook() {
        // When
        Optional<Category> categoryOptional = categoryRepository.findByDescription(BOOK_CATEGORY_DESCRIPTION);

        // Then
        assertEquals(BOOK_CATEGORY_DESCRIPTION, categoryOptional.get().getDescription());
    }

    @Test
    void findByDescriptionElectronics() {
        // When
        Optional<Category> categoryOptional = categoryRepository.findByDescription(ELECTRONICS_CATEGORY_DESCRIPTION);

        // Then
        assertEquals(ELECTRONICS_CATEGORY_DESCRIPTION, categoryOptional.get().getDescription());
    }

    @Test
    void findByDescriptionClothing() {
        // When
        Optional<Category> categoryOptional = categoryRepository.findByDescription(CLOTHING_CATEGORY_DESCRIPTION);

        // Then
        assertEquals(CLOTHING_CATEGORY_DESCRIPTION, categoryOptional.get().getDescription());
    }
}