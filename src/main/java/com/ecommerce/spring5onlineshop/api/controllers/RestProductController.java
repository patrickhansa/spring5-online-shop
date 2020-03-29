package com.ecommerce.spring5onlineshop.api.controllers;

import com.ecommerce.spring5onlineshop.api.mapper.ProductMapper;
import com.ecommerce.spring5onlineshop.api.model.ProductDTO;
import com.ecommerce.spring5onlineshop.api.model.ProductSetDTO;
import com.ecommerce.spring5onlineshop.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/product", produces = "application/json")
public class RestProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public RestProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/showAll")
    @ResponseStatus(HttpStatus.OK)
    public ProductSetDTO getAllProducts() {
        return new ProductSetDTO(
                productService
                .getProducts()
                .stream()
                .map(productMapper::productToProductDTO)
                .collect(Collectors.toSet()));
    }

    @GetMapping("/{id}/show")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@PathVariable String id) {
        return productMapper.productToProductDTO(productService.findById(Long.valueOf(id)));
    }
}
