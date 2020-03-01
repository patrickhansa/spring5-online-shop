package com.ecommerce.spring5onlineshop.commands;

import com.ecommerce.spring5onlineshop.model.Authority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class UserCommand {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String gender;
    private String birthDate;
    private String address;
    private ShoppingCartCommand shoppingCart;
    private Set<Authority> authorities = new HashSet<>();
}
