package com.ecommerce.spring5onlineshop.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

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
    private ShoppingCartDTO shoppingCartDTO;
    private Set<AuthorityDTO> authorityDTOSet;
}
