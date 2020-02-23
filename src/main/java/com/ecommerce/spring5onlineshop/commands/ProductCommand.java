package com.ecommerce.spring5onlineshop.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class ProductCommand {

    private Long id;
    private String name;
    private Float price;
    private Integer stock;
    private String description;
    private MultipartFile image;
    private Long shoppingCartId;
    private Set<CategoryCommand> categories = new HashSet<>();
}
