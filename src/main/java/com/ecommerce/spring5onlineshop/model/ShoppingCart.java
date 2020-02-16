package com.ecommerce.spring5onlineshop.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ShoppingCart extends BaseEntity {

    private Integer quantity;

    @OneToOne
    private Member member;

    @OneToMany(mappedBy = "shoppingCart")
    private Set<Product> products;
}
