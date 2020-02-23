package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.ProductCommand;
import com.ecommerce.spring5onlineshop.commands.ShoppingCartCommand;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartCommandToShoppingCartTest {

    private static final Long ID_VALUE = 1L;
    private static final Integer QUANTITY = 12;
    private static final Long PRODUCT_ID_1 = 2L;
    private static final Long PRODUCT_ID_2 = 3L;

    @Mock
    MockMultipartFile image;

    ShoppingCartCommandToShoppingCart converter;

    @BeforeEach
    void setUp() {
        converter = new ShoppingCartCommandToShoppingCart(
                        new ProductCommandToProduct(
                            new CategoryCommandToCategory()));
        image = new MockMultipartFile("file", "orig", null, "bar".getBytes());
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new ShoppingCartCommand()));
    }

    @Test
    void convert() {
        // Given
        ShoppingCartCommand shoppingCartCommand = new ShoppingCartCommand();
        shoppingCartCommand.setId(ID_VALUE);
        shoppingCartCommand.setQuantity(QUANTITY);
        ProductCommand productCommand1 = new ProductCommand();
        productCommand1.setId(PRODUCT_ID_1);
        productCommand1.setImage(image);
        ProductCommand productCommand2 = new ProductCommand();
        productCommand2.setId(PRODUCT_ID_2);
        productCommand2.setImage(image);
        shoppingCartCommand.getProducts().add(productCommand1);
        shoppingCartCommand.getProducts().add(productCommand2);

        // When
        ShoppingCart shoppingCart = converter.convert(shoppingCartCommand);

        // Then
        assertNotNull(shoppingCart);
        assertNotNull(shoppingCart.getProducts());
        assertEquals(ID_VALUE, shoppingCart.getId());
        assertEquals(QUANTITY, shoppingCart.getQuantity());
        assertEquals(2, shoppingCart.getProducts().size());
    }
}