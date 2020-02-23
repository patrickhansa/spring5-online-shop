package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.CategoryCommand;
import com.ecommerce.spring5onlineshop.commands.ProductCommand;
import com.ecommerce.spring5onlineshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductCommandToProductTest {

    private static final Long ID_VALUE = 1L;
    private static final String NAME = "TV";
    private static final Float PRICE = 99.99F;
    private static final Integer STOCK = 12;
    private static final String DESCRIPTION = "Foo";
    private static final Long SHOPPING_CART_ID = 2L;

    @Mock
    MockMultipartFile image;

    ProductCommandToProduct converter;

    @BeforeEach
    void setUp() {
        converter = new ProductCommandToProduct(new CategoryCommandToCategory());
        image = new MockMultipartFile("file", "orig", null, "bar".getBytes());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        // Given
        ProductCommand productCommand = new ProductCommand();
        productCommand.setImage(image);

        // Then
        assertNotNull(converter.convert(productCommand));
    }

    @Test
    void convert() {
        // Given
        ProductCommand productCommand = new ProductCommand();
        productCommand.setId(ID_VALUE);
        productCommand.setName(NAME);
        productCommand.setPrice(PRICE);
        productCommand.setStock(STOCK);
        productCommand.setDescription(DESCRIPTION);
        productCommand.setShoppingCartId(SHOPPING_CART_ID);
        productCommand.setImage(image);
        Set<CategoryCommand> categories = Stream.of(new CategoryCommand(), new CategoryCommand())
                .collect(Collectors.toCollection(HashSet::new));
        productCommand.setCategories(categories);

        // When
        Product product = converter.convert(productCommand);

        // Then
        assertNotNull(product);
        assertNotNull(product.getCategories());
        assertEquals(2, product.getCategories().size());
        assertEquals(ID_VALUE, product.getId());
        assertEquals(NAME, product.getName());
        assertEquals(PRICE, product.getPrice());
        assertEquals(STOCK, product.getStock());
        assertEquals(DESCRIPTION, product.getDescription());
        assertEquals(SHOPPING_CART_ID, product.getShoppingCart().getId());
    }
}