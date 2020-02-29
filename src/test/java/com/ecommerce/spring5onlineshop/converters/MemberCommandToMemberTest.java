package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.MemberCommand;
import com.ecommerce.spring5onlineshop.commands.ShoppingCartCommand;
import com.ecommerce.spring5onlineshop.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberCommandToMemberTest {

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

    MemberCommandToMember converter;

    @BeforeEach
    void setUp() {
        converter = new MemberCommandToMember(
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
        assertNotNull(converter.convert(new MemberCommand()));
    }

    @Test
    void convert() {
        // Given
        MemberCommand memberCommand = new MemberCommand();
        memberCommand.setId(ID_VALUE);
        memberCommand.setUsername(USER_NAME);
        memberCommand.setPassword(PASSWORD);
        memberCommand.setEmail(EMAIL);
        memberCommand.setFirstName(FIRST_NAME);
        memberCommand.setLastName(LAST_NAME);
        memberCommand.setPhone(PHONE);
        memberCommand.setGender(GENDER);
        memberCommand.setBirthDate(BIRTH_DATE);
        memberCommand.setAddress(ADDRESS);
        ShoppingCartCommand shoppingCartCommand = new ShoppingCartCommand();
        shoppingCartCommand.setId(SHOPPING_CART_ID);
        memberCommand.setShoppingCart(shoppingCartCommand);

        // When
        Member member = converter.convert(memberCommand);

        // Then
        assertNotNull(member);
        assertNotNull(member.getShoppingCart());
        assertEquals(ID_VALUE, member.getId());
        assertEquals(USER_NAME, member.getUsername());
        assertEquals(PASSWORD, member.getPassword());
        assertEquals(EMAIL, member.getEmail());
        assertEquals(FIRST_NAME, member.getFirstName());
        assertEquals(LAST_NAME, member.getLastName());
        assertEquals(PHONE, member.getPhone());
        assertEquals(GENDER, member.getGender());
        assertEquals(BIRTH_DATE, member.getBirthDate());
        assertEquals(ADDRESS, member.getAddress());
        assertEquals(SHOPPING_CART_ID, member.getShoppingCart().getId());
    }
}