package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.commands.ShoppingCartCommand;

public interface ShoppingCartService {

    ShoppingCartCommand saveShoppingCartCommand(ShoppingCartCommand command);

    ShoppingCartCommand addProductToShoppingCart(Long productId, Long shoppingCartId);
}
