package com.ecommerce.spring5onlineshop.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class ShoppingCartCommand {

    private Long id;
    private Integer quantity;
    private Set<ProductCommand> products = new HashSet<>();
}
