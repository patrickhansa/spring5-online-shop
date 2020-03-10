package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.commands.ProductCommand;
import com.ecommerce.spring5onlineshop.model.Product;

import java.util.Set;

public interface ProductService {
    Set<Product> getProducts();

    Product findById(Long l);

    ProductCommand findCommandById(Long l);

    ProductCommand saveProductCommand(ProductCommand command);

    void deleteById(Long l);

    Set<Product> listProductsByName(String productName);
}
