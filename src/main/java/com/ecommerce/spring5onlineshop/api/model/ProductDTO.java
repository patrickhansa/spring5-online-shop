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
public class ProductDTO {

    private Long id;
    private String name;
    private Float price;
    private Integer stock;
    private String description;
    private String image;
    private Long shoppingCartId;
    private Set<CategoryDTO> categories = new HashSet<>();
}
