package com.ecommerce.spring5onlineshop.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product extends BaseEntity {

    private String name;
    private Float price;
    private Integer stock;

    @Lob
    private String description;

    @Lob
    private Byte[] image;

    @ManyToOne
    private ShoppingCart shoppingCart;

    @ManyToMany(targetEntity=Category.class)
    private Set<Category> categories = new HashSet<>();
}
