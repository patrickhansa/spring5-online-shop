package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.model.Product;

import java.util.Set;

public interface ProductService {
    public Set<Product> getProducts();
}
