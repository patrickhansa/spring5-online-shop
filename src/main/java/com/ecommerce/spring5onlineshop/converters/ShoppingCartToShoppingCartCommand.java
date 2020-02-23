package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.ShoppingCartCommand;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartToShoppingCartCommand implements Converter<ShoppingCart, ShoppingCartCommand> {

    private final ProductToProductCommand productConverter;

    public ShoppingCartToShoppingCartCommand(ProductToProductCommand productConverter) {
        this.productConverter = productConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public ShoppingCartCommand convert(ShoppingCart source) {
        if(source == null) {
            return null;
        }

        final ShoppingCartCommand shoppingCartCommand = new ShoppingCartCommand();
        shoppingCartCommand.setId(source.getId());
        shoppingCartCommand.setQuantity(source.getQuantity());

        if (source.getProducts() != null && source.getProducts().size() > 0) {
            source.getProducts()
                    .forEach(product ->
                            shoppingCartCommand.getProducts().add(productConverter.convert(product)));
        }

        return shoppingCartCommand;
    }
}
