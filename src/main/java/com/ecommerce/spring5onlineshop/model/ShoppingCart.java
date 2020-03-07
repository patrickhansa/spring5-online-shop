package com.ecommerce.spring5onlineshop.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ShoppingCart extends BaseEntity {

    private Integer quantity;

    @ManyToMany(targetEntity = Product.class)
    private Set<Product> products = new HashSet<>();
}
