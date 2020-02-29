package com.ecommerce.spring5onlineshop.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MemberCommand {

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
}
