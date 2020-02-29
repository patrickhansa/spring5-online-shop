package com.ecommerce.spring5onlineshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorityTest {

    Authority authority;

    @BeforeEach
    void setUp() {
        authority = new Authority();
    }

    @Test
    void getAuthority() {
        // When
        authority.setName(AuthorityType.ROLE_ADMIN);

        // Then
        assertEquals(AuthorityType.ROLE_ADMIN, authority.getName());
    }
}