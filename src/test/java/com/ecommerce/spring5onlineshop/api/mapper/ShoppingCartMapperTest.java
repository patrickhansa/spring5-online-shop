package com.ecommerce.spring5onlineshop.api.mapper;

import com.ecommerce.spring5onlineshop.api.model.CategoryDTO;
import com.ecommerce.spring5onlineshop.api.model.ProductDTO;
import com.ecommerce.spring5onlineshop.api.model.ShoppingCartDTO;
import com.ecommerce.spring5onlineshop.model.Category;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Base64;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShoppingCartMapperTest {

    ShoppingCartMapper shoppingCartMapper = ShoppingCartMapper.INSTANCE;
    ProductMapper productMapper = ProductMapper.INSTANCE;

    public static final Integer QUANTITY = 3;
    public static final String NAME = "Something";
    public static final Float PRICE = 12.32F;
    public static final Integer STOCK = 3;
    public static final String DESCRIPTION = "Foo";
    public static final String CATEGORY = "category";
    public static final String STRING_IMAGE = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z8BQDwAEhQGAhKmMIQAAAABJRU5ErkJggg==";
    public static final Byte[] IMAGE = new Byte[] {0x12, 0x13};
    public static final byte[] PRIMITIVE_IMAGE = new byte[] {0x12, 0x13};

    @Test
    void shoppingCartToShoppingCartDTO() {
        // Given
        Product product = Product.builder()
                .name(NAME)
                .price(PRICE)
                .stock(STOCK)
                .description(DESCRIPTION)
                .image(IMAGE)
                .categories(Set.of(Category.builder().description(CATEGORY).build()))
                .build();

        ShoppingCart shoppingCart = ShoppingCart.builder()
                .quantity(QUANTITY)
                .products(Set.of(product))
                .build();

        // When
        ShoppingCartDTO shoppingCartDTO = shoppingCartMapper.shoppingCartToShoppingCartDTO(shoppingCart);

        // Then
        ProductDTO productDTO = shoppingCartDTO.getProducts().iterator().next();
        assertEquals(NAME, productDTO.getName());
        assertEquals(PRICE, productDTO.getPrice());
        assertEquals(STOCK, productDTO.getStock());
        assertEquals(DESCRIPTION, productDTO.getDescription());
        assertEquals(CATEGORY, productDTO.getCategories().iterator().next().getDescription());
        assertEquals(Base64.getEncoder().encodeToString(PRIMITIVE_IMAGE), productDTO.getImage());

        assertEquals(QUANTITY, shoppingCartDTO.getQuantity());
    }

    @Test
    void shoppingCartDTOToShoppingCart() {
        // Given
        ProductDTO productDTO = ProductDTO.builder()
                .name(NAME)
                .price(PRICE)
                .stock(STOCK)
                .description(DESCRIPTION)
                .image(STRING_IMAGE)
                .categories(Set.of(CategoryDTO.builder().description(CATEGORY).build()))
                .build();

        ShoppingCartDTO shoppingCartDTO = ShoppingCartDTO.builder()
                .quantity(QUANTITY)
                .products(Set.of(productDTO))
                .build();

        // When
        ShoppingCart shoppingCart = shoppingCartMapper.shoppingCartDTOToShoppingCart(shoppingCartDTO);

        // Then
        Product product = shoppingCart.getProducts().iterator().next();
        assertEquals(NAME, product.getName());
        assertEquals(PRICE, product.getPrice());
        assertEquals(STOCK, product.getStock());
        assertEquals(DESCRIPTION, product.getDescription());
        assertEquals(CATEGORY, product.getCategories().iterator().next().getDescription());
        assertEquals(Arrays.toString(productMapper.convertStringToImage(STRING_IMAGE)), Arrays.toString(product.getImage()));

        assertEquals(QUANTITY, shoppingCart.getQuantity());
    }
}