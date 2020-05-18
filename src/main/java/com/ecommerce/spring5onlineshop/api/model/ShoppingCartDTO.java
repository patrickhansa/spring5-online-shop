package com.ecommerce.spring5onlineshop.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartDTO {

    private Long id;
    private Integer quantity;
    private Set<ProductDTO> products = new HashSet<>();
}
