package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.ShoppingCartCommand;
import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserCommandToUserTest {

    private static final Long ID_VALUE = 1L;
    private static final String USER_NAME = "John";
    private static final String PASSWORD = "asfewrvx";
    private static final String EMAIL = "some@dot.com";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Smith";
    private static final String PHONE = "02343251243";
    private static final String GENDER = "male";
    private static final String BIRTH_DATE = "123432";
    private static final String ADDRESS = "First street, Illinois";
    private static final Long SHOPPING_CART_ID = 2L;

    UserCommandToUser converter;

    @BeforeEach
    void setUp() {
        converter = new UserCommandToUser(
                        new ShoppingCartCommandToShoppingCart(
                            new ProductCommandToProduct(
                                new CategoryCommandToCategory())));
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new UserCommand()));
    }

    @Test
    void convert() {
        // Given
        UserCommand userCommand = new UserCommand();
        userCommand.setId(ID_VALUE);
        userCommand.setUsername(USER_NAME);
        userCommand.setPassword(PASSWORD);
        userCommand.setEmail(EMAIL);
        userCommand.setFirstName(FIRST_NAME);
        userCommand.setLastName(LAST_NAME);
        userCommand.setPhone(PHONE);
        userCommand.setGender(GENDER);
        userCommand.setBirthDate(BIRTH_DATE);
        userCommand.setAddress(ADDRESS);
        ShoppingCartCommand shoppingCartCommand = new ShoppingCartCommand();
        shoppingCartCommand.setId(SHOPPING_CART_ID);
        userCommand.setShoppingCart(shoppingCartCommand);

        // When
        User user = converter.convert(userCommand);

        // Then
        assertNotNull(user);
        assertNotNull(user.getShoppingCart());
        assertEquals(ID_VALUE, user.getId());
        assertEquals(USER_NAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(PHONE, user.getPhone());
        assertEquals(GENDER, user.getGender());
        assertEquals(BIRTH_DATE, user.getBirthDate());
        assertEquals(ADDRESS, user.getAddress());
        assertEquals(SHOPPING_CART_ID, user.getShoppingCart().getId());
    }
}