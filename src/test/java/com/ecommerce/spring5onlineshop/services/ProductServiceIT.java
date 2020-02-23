package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.commands.ProductCommand;
import com.ecommerce.spring5onlineshop.converters.ProductCommandToProduct;
import com.ecommerce.spring5onlineshop.converters.ProductToProductCommand;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductServiceIT {

    public static final String NEW_DESCRIPTION = "New description";

    @Mock
    MockMultipartFile image;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCommandToProduct productCommandToProduct;

    @Autowired
    ProductToProductCommand productToProductCommand;

    @BeforeEach
    void setUp() {
        image =  new MockMultipartFile("file", "orig", null, "bar".getBytes());
    }

    @Transactional
    @Test
    public void testSaveOfDescription() {
        // Given
        Iterable<Product> products = productRepository.findAll();
        Product testProduct = products.iterator().next();
        ProductCommand testProductCommand = productToProductCommand.convert(testProduct);

        // When
        testProductCommand.setDescription(NEW_DESCRIPTION);
        testProductCommand.setImage(image);
        ProductCommand savedProductCommand = productService.saveProductCommand(testProductCommand);

        // Then
        assertEquals(NEW_DESCRIPTION, savedProductCommand.getDescription());
        assertEquals(testProduct.getId(), savedProductCommand.getId());
        assertEquals(testProduct.getName(), savedProductCommand.getName());
        assertEquals(testProduct.getPrice(), savedProductCommand.getPrice());
        assertEquals(testProduct.getStock(), savedProductCommand.getStock());
        assertEquals(testProduct.getCategories().size(), savedProductCommand.getCategories().size());
    }
}
