package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import com.ecommerce.spring5onlineshop.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserToUserCommandTest {

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

    UserToUserCommand converter;

    @BeforeEach
    void setUp() {
        converter = new UserToUserCommand(
                        new ShoppingCartToShoppingCartCommand(
                            new ProductToProductCommand(
                                new CategoryToCategoryCommand())));
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new User()));
    }

    @Test
    void convert() {
        // Given
        User user = new User();
        user.setId(ID_VALUE);
        user.setUsername(USER_NAME);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);
        user.setPhone(PHONE);
        user.setGender(GENDER);
        user.setBirthDate(BIRTH_DATE);
        user.setAddress(ADDRESS);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(SHOPPING_CART_ID);
        user.setShoppingCart(shoppingCart);

        // When
        UserCommand userCommand = converter.convert(user);

        // Then
        assertNotNull(userCommand);
        assertNotNull(userCommand.getShoppingCart());
        assertEquals(ID_VALUE, userCommand.getId());
        assertEquals(USER_NAME, userCommand.getUsername());
        assertEquals(PASSWORD, userCommand.getPassword());
        assertEquals(EMAIL, userCommand.getEmail());
        assertEquals(FIRST_NAME, userCommand.getFirstName());
        assertEquals(LAST_NAME, userCommand.getLastName());
        assertEquals(PHONE, userCommand.getPhone());
        assertEquals(GENDER, userCommand.getGender());
        assertEquals(BIRTH_DATE, userCommand.getBirthDate());
        assertEquals(ADDRESS, userCommand.getAddress());
        assertEquals(SHOPPING_CART_ID, userCommand.getShoppingCart().getId());
    }
}