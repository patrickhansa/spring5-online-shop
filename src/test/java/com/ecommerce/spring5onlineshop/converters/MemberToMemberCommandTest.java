package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.MemberCommand;
import com.ecommerce.spring5onlineshop.model.Member;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberToMemberCommandTest {

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

    MemberToMemberCommand converter;

    @BeforeEach
    void setUp() {
        converter = new MemberToMemberCommand(
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
        assertNotNull(converter.convert(new Member()));
    }

    @Test
    void convert() {
        // Given
        Member member = new Member();
        member.setId(ID_VALUE);
        member.setUserName(USER_NAME);
        member.setPassword(PASSWORD);
        member.setEmail(EMAIL);
        member.setFirstName(FIRST_NAME);
        member.setLastName(LAST_NAME);
        member.setPhone(PHONE);
        member.setGender(GENDER);
        member.setBirthDate(BIRTH_DATE);
        member.setAddress(ADDRESS);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(SHOPPING_CART_ID);
        member.setShoppingCart(shoppingCart);

        // When
        MemberCommand memberCommand = converter.convert(member);

        // Then
        assertNotNull(memberCommand);
        assertNotNull(memberCommand.getShoppingCart());
        assertEquals(ID_VALUE, memberCommand.getId());
        assertEquals(USER_NAME, memberCommand.getUserName());
        assertEquals(PASSWORD, memberCommand.getPassword());
        assertEquals(EMAIL, memberCommand.getEmail());
        assertEquals(FIRST_NAME, memberCommand.getFirstName());
        assertEquals(LAST_NAME, memberCommand.getLastName());
        assertEquals(PHONE, memberCommand.getPhone());
        assertEquals(GENDER, memberCommand.getGender());
        assertEquals(BIRTH_DATE, memberCommand.getBirthDate());
        assertEquals(ADDRESS, memberCommand.getAddress());
        assertEquals(SHOPPING_CART_ID, memberCommand.getShoppingCart().getId());
    }
}