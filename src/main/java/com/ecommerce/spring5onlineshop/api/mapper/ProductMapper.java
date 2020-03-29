package com.ecommerce.spring5onlineshop.api.mapper;

import com.ecommerce.spring5onlineshop.api.model.ProductDTO;
import com.ecommerce.spring5onlineshop.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Base64;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mappings({
            @Mapping(source = "product", target = "image", qualifiedByName = "convertByteImage"),
            @Mapping(target = "shoppingCartId", source = "")
    })
    ProductDTO productToProductDTO(Product product);

    @Named("convertByteImage")
    default String convertByteImage(Product product) {
        // Convert the product image from a Byte[] array
        // to a byte[] array
        Byte[] byteObjects = product.getImage();
        byte[] bytes = new byte[byteObjects.length];
        int j = 0;

        for (Byte b : byteObjects) {
            bytes[j++] = b;
        }

        // Convert the image from a byte[] array to a string
        // so that it can be sent inside a JSON body
        return Base64.getEncoder().encodeToString(bytes);
    }
}
