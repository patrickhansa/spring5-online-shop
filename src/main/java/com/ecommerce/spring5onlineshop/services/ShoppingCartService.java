package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.commands.ShoppingCartCommand;
import com.ecommerce.spring5onlineshop.model.Product;

import java.util.Set;

public interface ShoppingCartService {

    ShoppingCartCommand saveShoppingCartCommand(ShoppingCartCommand command);

    ShoppingCartCommand addProductToShoppingCart(Long productId, Long shoppingCartId);

    Set<Product> getProductsInShoppingCart(Long shoppingCartId);

    Float getShoppingCartPrice(String username);

    void placeOrder(Long shoppingCartId);

    void removeItemFromShoppingCart(Long shoppingCartId, Long productId);
}
