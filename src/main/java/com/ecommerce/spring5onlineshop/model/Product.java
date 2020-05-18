package com.ecommerce.spring5onlineshop.model;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product implements Comparable<Product>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Float price;
    private Integer stock;

    @Lob
    private String description;

    @Lob
    private Byte[] image;

    @ManyToMany(targetEntity=Category.class)
    private Set<Category> categories = new HashSet<>();

    @Override
    public int compareTo(@NotNull Product o) {
        if (this.name == null) {
            return -1;
        }
        return this.name.compareTo(o.getName());
    }
}
