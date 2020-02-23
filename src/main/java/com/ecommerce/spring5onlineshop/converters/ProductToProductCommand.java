package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.ProductCommand;
import com.ecommerce.spring5onlineshop.model.Product;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductCommand implements Converter<Product, ProductCommand> {

    private final CategoryToCategoryCommand categoryConverter;

    public ProductToProductCommand(CategoryToCategoryCommand categoryConverter) {
        this.categoryConverter = categoryConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public ProductCommand convert(Product source) {
        if (source == null) {
            return null;
        }

        final ProductCommand productCommand = new ProductCommand();
        productCommand.setId(source.getId());
        productCommand.setName(source.getName());
        productCommand.setPrice(source.getPrice());
        productCommand.setStock(source.getStock());
        productCommand.setDescription(source.getDescription());

        if (source.getShoppingCart() != null) {
            productCommand.setShoppingCartId(source.getShoppingCart().getId());
        }

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(category ->
                            productCommand.getCategories().add(categoryConverter.convert(category)));
        }

        return productCommand;
    }
}
