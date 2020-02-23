package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.ProductCommand;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ProductCommandToProduct implements Converter<ProductCommand, Product> {

    private final CategoryCommandToCategory categoryCommandConverter;

    public ProductCommandToProduct(CategoryCommandToCategory categoryCommandConverter) {
        this.categoryCommandConverter = categoryCommandConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Product convert(ProductCommand source) {
        if (source == null) {
            return null;
        }

        final Product product = new Product();
        product.setId(source.getId());
        product.setName(source.getName());
        product.setPrice(source.getPrice());
        product.setStock(source.getStock());
        product.setDescription(source.getDescription());
        product.setImage(source.getImage());

        if (source.getShoppingCartId() != null) {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setId(source.getShoppingCartId());
            product.setShoppingCart(shoppingCart);
            shoppingCart.getProducts().add(product);
        }

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(category -> product.getCategories().add(categoryCommandConverter.convert(category)));
        }

        return product;
    }
}
