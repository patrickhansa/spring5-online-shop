package com.ecommerce.spring5onlineshop.api.model;

import com.ecommerce.spring5onlineshop.model.AuthorityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDTO {

    private Long id;
    private AuthorityType name;

    public AuthorityDTO(AuthorityType authorityType) {
        this.name = authorityType;
    }
}
