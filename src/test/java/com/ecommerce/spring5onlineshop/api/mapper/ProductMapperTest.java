package com.ecommerce.spring5onlineshop.api.mapper;

import com.ecommerce.spring5onlineshop.api.model.ProductDTO;
import com.ecommerce.spring5onlineshop.model.Category;
import com.ecommerce.spring5onlineshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperTest {

    public static final String NAME = "Mark";
    public static final Float PRICE = 12.32F;
    public static final Integer STOCK = 3;
    public static final String DESCRIPTION = "Foo";
    public static final String CATEGORY = "category";
    public static final Byte[] IMAGE = new Byte[] {0x12, 0x13};
    public static final byte[] PRIMITIVE_IMAGE = new byte[] {0x12, 0x13};

    ProductMapper productMapper = ProductMapper.INSTANCE;

    @BeforeEach
    void setUp() {
    }

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
}