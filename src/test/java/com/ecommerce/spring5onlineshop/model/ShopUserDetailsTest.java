package com.ecommerce.spring5onlineshop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShopUserDetailsTest {
    public static final String USERNAME = "Mark";
    public static final String PASSWORD = "werew";

    ShopUserDetails shopUserDetails;

    @Test
    void getUsername() {
        // When
        shopUserDetails = new ShopUserDetails(User.builder().username(USERNAME).build());

        // Then
        assertEquals(USERNAME, shopUserDetails.getUsername());
    }

    @Test
    void getPassword() {
        // When
        shopUserDetails = new ShopUserDetails(User.builder().password(PASSWORD).build());

        // Then
        assertEquals(PASSWORD, shopUserDetails.getPassword());
    }
}