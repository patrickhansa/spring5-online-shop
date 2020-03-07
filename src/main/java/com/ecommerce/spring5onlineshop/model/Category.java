package com.ecommerce.spring5onlineshop.model;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Proxy(lazy = false)
public class Category extends BaseEntity {

    private String description;
}
