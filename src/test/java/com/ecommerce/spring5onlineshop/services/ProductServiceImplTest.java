package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void getProductsTest() {
        // Given
        Product product = new Product();
        Set<Product> productData = new HashSet<>();
        productData.add(product);

        when(productService.getProducts()).thenReturn(productData);

        // When
        Set<Product> products = productService.getProducts();

        // Then
        assertEquals(products.size(), 1);
        verify(productRepository, times(1)).findAll();
        verify(productRepository, never()).findById(anyLong());
    }
}