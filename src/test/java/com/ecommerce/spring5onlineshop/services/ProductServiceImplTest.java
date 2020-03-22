package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.converters.ProductCommandToProduct;
import com.ecommerce.spring5onlineshop.converters.ProductToProductCommand;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.repositories.ProductRepository;
import com.ecommerce.spring5onlineshop.repositories.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ShoppingCartRepository shoppingCartRepository;

    @Mock
    ProductToProductCommand productToProductCommand;

    @Mock
    ProductCommandToProduct productCommandToProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        productService = new ProductServiceImpl(productRepository, shoppingCartRepository,
                productCommandToProduct, productToProductCommand);
    }

    @Test
    void getProductByIdTest() {
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
        product.setStock(2);
        Set<Product> productData = new TreeSet<>();
        productData.add(product);

        when(productService.getProducts()).thenReturn(productData);

        // When
        Set<Product> products = productService.getProducts();

        // Then
        assertEquals(products.size(), 1);
        verify(productRepository, times(1)).findAll();
        verify(productRepository, never()).findById(anyLong());
    }

    @Test
    public void testDeleteById() {
        // Given
        Long idToDelete = 2L;

        // When
        productService.deleteById(idToDelete);

        // Then
        verify(productRepository, times(1)).deleteById(idToDelete);
        verify(shoppingCartRepository, times(1)).getShoppingCartsByProductId(idToDelete);
    }

    @Test
    void listProductsByName() {
        // Given
        String productName = "foo";

        // When
        productService.listProductsByName(productName);

        // Then
        verify(productRepository, times(1)).findByNameContainingIgnoreCase(productName);
    }
}