package com.ecommerce.spring5onlineshop.api.mapper;

import com.ecommerce.spring5onlineshop.api.model.CategoryDTO;
import com.ecommerce.spring5onlineshop.api.model.ProductDTO;
import com.ecommerce.spring5onlineshop.model.Category;
import com.ecommerce.spring5onlineshop.model.Product;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Base64;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperTest {

    public static final String NAME = "Mark";
    public static final Float PRICE = 12.32F;
    public static final Integer STOCK = 3;
    public static final String DESCRIPTION = "Foo";
    public static final String CATEGORY = "category";
    public static final String STRING_IMAGE = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z8BQDwAEhQGAhKmMIQAAAABJRU5ErkJggg==";
    public static final Byte[] IMAGE = new Byte[] {0x12, 0x13};
    public static final byte[] PRIMITIVE_IMAGE = new byte[] {0x12, 0x13};

    ProductMapper productMapper = ProductMapper.INSTANCE;

    @Test
    void productToProductDTO() {
        // Given
        Product product = Product.builder()
                .name(NAME)
                .price(PRICE)
                .stock(STOCK)
                .description(DESCRIPTION)
                .image(IMAGE)
                .categories(Set.of(Category.builder().description(CATEGORY).build()))
                .build();

        // When
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        // Then
        assertEquals(NAME, productDTO.getName());
        assertEquals(PRICE, productDTO.getPrice());
        assertEquals(STOCK, productDTO.getStock());
        assertEquals(DESCRIPTION, productDTO.getDescription());
        assertEquals(CATEGORY, productDTO.getCategories().iterator().next().getDescription());
        assertEquals(Base64.getEncoder().encodeToString(PRIMITIVE_IMAGE), productDTO.getImage());
    }

    @Test
    void productDTOToProduct() {
        // Given
        ProductDTO productDTO = ProductDTO.builder()
                .name(NAME)
                .price(PRICE)
                .stock(STOCK)
                .description(DESCRIPTION)
                .image(STRING_IMAGE)
                .categories(Set.of(CategoryDTO.builder().description(CATEGORY).build()))
                .build();

        // When
        Product product = productMapper.productDTOToProduct(productDTO);

        // Then
        assertEquals(NAME, product.getName());
        assertEquals(PRICE, product.getPrice());
        assertEquals(STOCK, product.getStock());
        assertEquals(DESCRIPTION, product.getDescription());
        assertEquals(CATEGORY, product.getCategories().iterator().next().getDescription());
        assertEquals(Arrays.toString(productMapper.convertStringToImage(STRING_IMAGE)), Arrays.toString(product.getImage()));
    }
}