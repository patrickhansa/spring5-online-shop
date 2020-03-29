package com.ecommerce.spring5onlineshop.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSetDTO {
    Set<ProductDTO> products;
}
