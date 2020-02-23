package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.CategoryCommand;
import com.ecommerce.spring5onlineshop.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    private static final String DESCRIPTION = "description";
    private static final Long ID_VALUE = 1L;

    CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        // Given
        CategoryCommand command = new CategoryCommand();
        command.setId(ID_VALUE);
        command.setDescription(DESCRIPTION);

        // When
        Category category = converter.convert(command);

        // Then
        assertNotNull(category);
        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}