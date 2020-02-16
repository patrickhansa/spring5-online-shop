package com.ecommerce.spring5onlineshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberTest {

    Member member;

    @BeforeEach
    void setUp() {
        member = new Member();
    }

    @Test
    void getUserName() {
        // Given
        String userName = "Moore";

        // When
        member.setUserName(userName);

        // Then
        assertEquals(userName, member.getUserName());
    }

    @Test
    void getPassword() {
        // Given
        String password = "213fdsarew";

        // When
        member.setPassword(password);

        // Then
        assertEquals(password, member.getPassword());
    }

    @Test
    void getEmail() {
        // Given
        String email = "foo";

        // When
        member.setEmail(email);

        // Then
        assertEquals(email, member.getEmail());
    }

    @Test
    void getPhone() {
        // Given
        String phone = "324324-23432-123";

        // When
        member.setPhone(phone);

        // Then
        assertEquals(phone, member.getPhone());
    }

    @Test
    void getGender() {
        // Given
        String gender = "foo";

        // When
        member.setGender(gender);

        // Then
        assertEquals(gender, member.getGender());
    }

    @Test
    void getBirthDate() {
        // Given
        String birthDate = "19-10-1977";

        // When
        member.setBirthDate(birthDate);

        // Then
        assertEquals(birthDate, member.getBirthDate());
    }

    @Test
    void getAddress() {
        // Given
        String address = "foo";

        // When
        member.setAddress(address);

        // Then
        assertEquals(address, member.getAddress());
    }

    @Test
    void getCart() {
        // Given
        Integer quantity = 1;
        ShoppingCart shoppingCart = ShoppingCart.builder().quantity(quantity).build();

        // When
        member.setShoppingCart(shoppingCart);

        // Then
        assertEquals(quantity, member.getShoppingCart().getQuantity());
    }
}