package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.ShoppingCartCommand;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartToShoppingCartCommandTest {

    private static final Long ID_VALUE = 1L;
    private static final Integer QUANTITY = 12;
    private static final Long PRODUCT_ID_1 = 2L;
    private static final Long PRODUCT_ID_2 = 3L;

    ShoppingCartToShoppingCartCommand converter;

    @BeforeEach
    void setUp() {
        converter = new ShoppingCartToShoppingCartCommand(
                        new ProductToProductCommand(
                            new CategoryToCategoryCommand()));
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new ShoppingCart()));
    }

    @Test
    void convert() {
        // Given
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(ID_VALUE);
        shoppingCart.setQuantity(QUANTITY);
        Product product1 = new Product();
        product1.setId(PRODUCT_ID_1);
        Product product2 = new Product();
        product2.setId(PRODUCT_ID_2);
        shoppingCart.getProducts().add(product1);
        shoppingCart.getProducts().add(product2);

        // When
        ShoppingCartCommand shoppingCartCommand = converter.convert(shoppingCart);

        // Then
        assertNotNull(shoppingCartCommand);
        assertNotNull(shoppingCartCommand.getProducts());
        assertEquals(ID_VALUE, shoppingCartCommand.getId());
        assertEquals(QUANTITY, shoppingCartCommand.getQuantity());
        assertEquals(2, shoppingCartCommand.getProducts().size());
    }
}