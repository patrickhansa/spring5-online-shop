package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.CategoryCommand;
import com.ecommerce.spring5onlineshop.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    private static final String DESCRIPTION = "description";
    private static final Long ID_VALUE = 1L;

    CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullParameter() {
        assertThrows(NullPointerException.class, () -> converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        // Given
        Category command = new Category();
        command.setId(ID_VALUE);
        command.setDescription(DESCRIPTION);

        // When
        CategoryCommand category = converter.convert(command);

        // Then
        assertNotNull(category);
        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}