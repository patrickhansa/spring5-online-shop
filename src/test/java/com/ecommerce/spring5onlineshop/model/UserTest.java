package com.ecommerce.spring5onlineshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void getUserName() {
        // Given
        String userName = "Moore";

        // When
        user.setUsername(userName);

        // Then
        assertEquals(userName, user.getUsername());
    }

    @Test
    void getPassword() {
        // Given
        String password = "213fdsarew";

        // When
        user.setPassword(password);

        // Then
        assertEquals(password, user.getPassword());
    }

    @Test
    void getEmail() {
        // Given
        String email = "foo";

        // When
        user.setEmail(email);

        // Then
        assertEquals(email, user.getEmail());
    }

    @Test
    void getPhone() {
        // Given
        String phone = "324324-23432-123";

        // When
        user.setPhone(phone);

        // Then
        assertEquals(phone, user.getPhone());
    }

    @Test
    void getGender() {
        // Given
        String gender = "foo";

        // When
        user.setGender(gender);

        // Then
        assertEquals(gender, user.getGender());
    }

    @Test
    void getBirthDate() {
        // Given
        String birthDate = "19-10-1977";

        // When
        user.setBirthDate(birthDate);

        // Then
        assertEquals(birthDate, user.getBirthDate());
    }

    @Test
    void getAddress() {
        // Given
        String address = "foo";

        // When
        user.setAddress(address);

        // Then
        assertEquals(address, user.getAddress());
    }

    @Test
    void getCart() {
        // Given
        Integer quantity = 1;
        ShoppingCart shoppingCart = ShoppingCart.builder().quantity(quantity).build();

        // When
        user.setShoppingCart(shoppingCart);

        // Then
        assertEquals(quantity, user.getShoppingCart().getQuantity());
    }

    @Test
    void getAuthorities() {
        // Given
        Authority authority = new Authority(AuthorityType.ROLE_USER);

        // When
        user.setAuthorities(Set.of(authority));

        // Then
        assertEquals(AuthorityType.ROLE_USER, user.getAuthorities().iterator().next().getName());
        assertEquals(1, user.getAuthorities().size());
    }
}