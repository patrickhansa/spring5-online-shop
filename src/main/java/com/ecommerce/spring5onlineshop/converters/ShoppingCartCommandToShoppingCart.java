package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.ShoppingCartCommand;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartCommandToShoppingCart implements Converter<ShoppingCartCommand, ShoppingCart> {

    private final ProductCommandToProduct productConverter;

    public ShoppingCartCommandToShoppingCart(ProductCommandToProduct productConverter) {
        this.productConverter = productConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public ShoppingCart convert(ShoppingCartCommand source) {

        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(source.getId());
        shoppingCart.setQuantity(source.getQuantity());

        if (source.getProducts() != null && source.getProducts().size() > 0) {
            source.getProducts()
                    .forEach(product ->
                            shoppingCart.getProducts().add(productConverter.convert(product)));
        }

        return shoppingCart;
    }
}
