package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.ProductCommand;
import com.ecommerce.spring5onlineshop.model.Product;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.IOException;

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

        final Product product = new Product();
        product.setId(source.getId());
        product.setName(source.getName());
        product.setPrice(source.getPrice());
        product.setStock(source.getStock());
        product.setDescription(source.getDescription());

        try {
            Byte[] byteObjects = new Byte[source.getImage().getBytes().length];

            int i = 0;

            for (byte b : source.getImage().getBytes()) {
                byteObjects[i++] = b;
            }

            product.setImage(byteObjects);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(category -> product.getCategories().add(categoryCommandConverter.convert(category)));
        }

        return product;
    }
}
