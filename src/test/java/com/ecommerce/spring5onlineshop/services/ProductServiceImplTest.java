package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.converters.ProductCommandToProduct;
import com.ecommerce.spring5onlineshop.converters.ProductToProductCommand;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductToProductCommand productToProductCommand;

    @Mock
    ProductCommandToProduct productCommandToProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        productService = new ProductServiceImpl(productRepository, productCommandToProduct, productToProductCommand);
    }

    @Test
    void getRecipeByIdTest() {
        // Given
        Product product = new Product();
        product.setId(1L);
        Optional<Product> productOptional = Optional.of(product);

        when(productRepository.findById(anyLong())).thenReturn(productOptional);

        // When
        Product productReturned = productService.findById(1L);

        // Then
        assertNotNull(productReturned, "Null product returned");
        verify(productRepository, times(1)).findById(anyLong());
        verify(productRepository, never()).findAll();
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