package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.ProductCommand;
import com.ecommerce.spring5onlineshop.model.Category;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductToProductCommandTest {

    private static final Long ID_VALUE = 1L;
    private static final String NAME = "TV";
    private static final Float PRICE = 99.99F;
    private static final Integer STOCK = 12;
    private static final String DESCRIPTION = "Foo";
    private static final Long SHOPPING_CART_ID = 2L;

    ProductToProductCommand converter;

    @BeforeEach
    void setUp() {
        converter = new ProductToProductCommand(new CategoryToCategoryCommand());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Product()));
    }

    @Test
    void convert() {
        // Given
        Product product = new Product();
        product.setId(ID_VALUE);
        product.setName(NAME);
        product.setPrice(PRICE);
        product.setStock(STOCK);
        product.setDescription(DESCRIPTION);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(SHOPPING_CART_ID);
        product.setShoppingCart(shoppingCart);
        Set<Category> categories = Stream.of(new Category(), new Category())
                .collect(Collectors.toCollection(HashSet::new));
        product.setCategories(categories);

        // When
        ProductCommand productCommand = converter.convert(product);

        // Then
        assertNotNull(productCommand);
        assertNotNull(productCommand.getCategories());
        assertEquals(2, productCommand.getCategories().size());
        assertEquals(ID_VALUE, productCommand.getId());
        assertEquals(NAME, productCommand.getName());
        assertEquals(PRICE, productCommand.getPrice());
        assertEquals(STOCK, productCommand.getStock());
        assertEquals(DESCRIPTION, productCommand.getDescription());
        assertEquals(SHOPPING_CART_ID, productCommand.getShoppingCartId());
    }
}