package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.converters.ShoppingCartCommandToShoppingCart;
import com.ecommerce.spring5onlineshop.converters.ShoppingCartToShoppingCartCommand;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.repositories.ProductRepository;
import com.ecommerce.spring5onlineshop.repositories.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ShoppingCartServiceImplTest {

    Float PRODUCT1_PRICE = 12.32F;
    Float PRODUCT2_PRICE = 32.22F;
    Float SUM = PRODUCT1_PRICE + PRODUCT2_PRICE;

    @Mock
    ShoppingCartCommandToShoppingCart shoppingCartCommandToShoppingCart;

    @Mock
    ShoppingCartToShoppingCartCommand shoppingCartToShoppingCartCommand;

    @Mock
    ShoppingCartRepository shoppingCartRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    UserService userService;

    ShoppingCartServiceImpl shoppingCartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        shoppingCartService = new ShoppingCartServiceImpl(shoppingCartCommandToShoppingCart,
                shoppingCartToShoppingCartCommand,
                shoppingCartRepository,
                productRepository,
                userService);
    }

    @Test
    void getProductsInShoppingCart() {
        // Given
        Product product = new Product();
        Set<Product> productSet = new HashSet<>();
        productSet.add(product);

        when(shoppingCartService.getProductsInShoppingCart(anyLong())).thenReturn(productSet);

        // When
        Set<Product> products = shoppingCartService.getProductsInShoppingCart(anyLong());

        // Then
        assertEquals(products.size(), 1);
        verify(productRepository, times(1)).getProductsByShoppingCartId(anyLong());
        verify(productRepository, never()).findProductById(anyLong());
    }

    @Test
    void getShoppingCartPrice() {
        // Given
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setPrice(PRODUCT1_PRICE);
        product2.setPrice(PRODUCT2_PRICE);
        Set<Product> productSet = Set.of(product1, product2);

        when(userService.findShoppingCartIdByUsername(anyString())).thenReturn(2L);

        when(productRepository.getProductsByShoppingCartId(anyLong())).thenReturn(productSet);

        // When
        Float price = shoppingCartService.getShoppingCartPrice(anyString());

        // Then
        assertEquals(productSet.size(), 2);
        assertEquals(price, SUM);
    }

    @Test
    void placeOrder() {
        // When
        shoppingCartService.placeOrder(anyLong());

        // Then
        verify(productRepository, times(1)).findAll();
        verify(productRepository, times(1)).saveAll(any());
    }


    @Test
    void removeItemFromShoppingCart() {
        // When
        shoppingCartService.removeItemFromShoppingCart(2L, 3L);

        // Then
        verify(productRepository, times(1)).getProductsByShoppingCartId(2L);
        verify(shoppingCartRepository, times(1)).findShoppingCartById(2L);
    }
}