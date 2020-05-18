package com.ecommerce.spring5onlineshop.api.mapper;

import com.ecommerce.spring5onlineshop.model.Product;
import org.mapstruct.Named;

import java.util.Base64;

public interface BaseMapper {

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

    @Named("convertStringToImage")
    default Byte[] convertStringToImage(String image) {
        byte[] decodedBytes = Base64.getDecoder().decode(image);
        Byte[] byteObjects = new Byte[decodedBytes.length];
        int j = 0;

        for (byte b : decodedBytes) {
            byteObjects[j++] = b;
        }

        return byteObjects;
    }
}
