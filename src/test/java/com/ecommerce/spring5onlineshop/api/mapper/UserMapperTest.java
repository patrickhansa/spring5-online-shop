package com.ecommerce.spring5onlineshop.api.mapper;

import com.ecommerce.spring5onlineshop.api.model.CategoryDTO;
import com.ecommerce.spring5onlineshop.api.model.ProductDTO;
import com.ecommerce.spring5onlineshop.api.model.ShoppingCartDTO;
import com.ecommerce.spring5onlineshop.api.model.UserDTO;
import com.ecommerce.spring5onlineshop.model.Category;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import com.ecommerce.spring5onlineshop.model.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Base64;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    UserMapper userMapper = UserMapper.INSTANCE;
    ProductMapper productMapper = ProductMapper.INSTANCE;

    public static final Integer QUANTITY = 3;
    public static final String PRODUCT_NAME = "Something";
    public static final Float PRICE = 12.32F;
    public static final Integer STOCK = 3;
    public static final String DESCRIPTION = "Foo";
    public static final String CATEGORY = "category";
    public static final String STRING_IMAGE = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z8BQDwAEhQGAhKmMIQAAAABJRU5ErkJggg==";
    public static final Byte[] IMAGE = new Byte[] {0x12, 0x13};
    public static final byte[] PRIMITIVE_IMAGE = new byte[] {0x12, 0x13};

    public static final String USERNAME = "Mark";
    public static final String PASSWORD = "sfsd";
    public static final String EMAIL = "someone@somewhere";
    public static final String FIRST_NAME = "Michael";
    public static final String LAST_NAME = "Strutt";
    public static final String PHONE = "03223232321";
    public static final String GENDER = "M";
    public static final String BIRTH_DATE = "12/04/1990";
    public static final String ADDRESS = "First street";

    @Test
    void userToUserDTO() {
        // Given
        Product product = Product.builder()
                .name(PRODUCT_NAME)
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

        User user = User.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phone(PHONE)
                .gender(GENDER)
                .birthDate(BIRTH_DATE)
                .address(ADDRESS)
                .shoppingCart(shoppingCart)
                .build();

        // When
        UserDTO userDTO = userMapper.userToUserDTO(user);

        // Then
        ShoppingCartDTO shoppingCartDTO = userDTO.getShoppingCartDTO();
        ProductDTO productDTO = shoppingCartDTO.getProducts().iterator().next();
        assertEquals(PRODUCT_NAME, productDTO.getName());
        assertEquals(PRICE, productDTO.getPrice());
        assertEquals(STOCK, productDTO.getStock());
        assertEquals(DESCRIPTION, productDTO.getDescription());
        assertEquals(CATEGORY, productDTO.getCategories().iterator().next().getDescription());
        assertEquals(Base64.getEncoder().encodeToString(PRIMITIVE_IMAGE), productDTO.getImage());
        assertEquals(QUANTITY, shoppingCart.getQuantity());

        assertEquals(USERNAME, userDTO.getUsername());
        assertEquals(PASSWORD, userDTO.getPassword());
        assertEquals(EMAIL, userDTO.getEmail());
        assertEquals(FIRST_NAME, userDTO.getFirstName());
        assertEquals(LAST_NAME, userDTO.getLastName());
        assertEquals(PHONE, userDTO.getPhone());
        assertEquals(GENDER, userDTO.getGender());
        assertEquals(BIRTH_DATE, userDTO.getBirthDate());
        assertEquals(ADDRESS, userDTO.getAddress());
    }

    @Test
    void userDTOToUser() {
        // Given
        ProductDTO productDTO = ProductDTO.builder()
                .name(PRODUCT_NAME)
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

        UserDTO userDTO = UserDTO.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .phone(PHONE)
                .gender(GENDER)
                .birthDate(BIRTH_DATE)
                .address(ADDRESS)
                .shoppingCartDTO(shoppingCartDTO)
                .build();

        // When
        User user = userMapper.userDTOToUser(userDTO);

        // Then
        ShoppingCart shoppingCart = user.getShoppingCart();
        Product product = shoppingCart.getProducts().iterator().next();
        assertEquals(PRODUCT_NAME, product.getName());
        assertEquals(PRICE, product.getPrice());
        assertEquals(STOCK, product.getStock());
        assertEquals(DESCRIPTION, product.getDescription());
        assertEquals(CATEGORY, product.getCategories().iterator().next().getDescription());
        assertEquals(Arrays.toString(productMapper.convertStringToImage(STRING_IMAGE)), Arrays.toString(product.getImage()));
        assertEquals(QUANTITY, shoppingCart.getQuantity());

        assertEquals(USERNAME, user.getUsername());
        assertEquals(PASSWORD, user.getPassword());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(PHONE, user.getPhone());
        assertEquals(GENDER, user.getGender());
        assertEquals(BIRTH_DATE, user.getBirthDate());
        assertEquals(ADDRESS, user.getAddress());
    }
}