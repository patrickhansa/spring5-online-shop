package com.ecommerce.spring5onlineshop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class ProductType extends BaseEntity {

    private String description;
}
