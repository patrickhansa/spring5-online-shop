package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.commands.ProductCommand;
import com.ecommerce.spring5onlineshop.converters.ProductCommandToProduct;
import com.ecommerce.spring5onlineshop.converters.ProductToProductCommand;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import com.ecommerce.spring5onlineshop.repositories.ProductRepository;
import com.ecommerce.spring5onlineshop.repositories.ShoppingCartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductCommandToProduct productCommandToProduct;
    private final ProductToProductCommand productToProductCommand;

    public ProductServiceImpl(ProductRepository productRepository,
                              ShoppingCartRepository shoppingCartRepository,
                              ProductCommandToProduct productCommandToProduct,
                              ProductToProductCommand productToProductCommand) {
        this.productRepository = productRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productCommandToProduct = productCommandToProduct;
        this.productToProductCommand = productToProductCommand;
    }

    @Override
    public Set<Product> getProducts() {
        Set<Product> productSet = new TreeSet<>();
        productRepository.findAll().iterator().forEachRemaining(productSet::add);
        productSet.removeIf(product -> product.getStock() == 0);

        return productSet;
    }

    @Override
    public Product findById(Long l) {

        Optional<Product> productOptional = productRepository.findById(l);

        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found");
        }

        return productOptional.get();
    }

    @Override
    @Transactional
    public ProductCommand findCommandById(Long l) {

        return productToProductCommand.convert(findById(l));
    }

    @Override
    @Transactional
    public ProductCommand saveProductCommand(ProductCommand command) {
        Product detachedProduct = productCommandToProduct.convert(command);

        Product savedProduct = productRepository.save(detachedProduct);
        log.debug("Saved product ID: " + savedProduct.getId());
        return productToProductCommand.convert(savedProduct);
    }

    @Override
    public void deleteById(Long idToDelete) {
        // Get the shopping carts which have this product
        Set<ShoppingCart> shoppingCarts = shoppingCartRepository.getShoppingCartsByProductId(idToDelete);

        // Delete the product from all the shopping carts
        for (ShoppingCart shoppingCart : shoppingCarts) {
            Set<Product> productSet = shoppingCart.getProducts();
            productSet.removeIf(product -> product.getId().equals(idToDelete));
        }

        // Delete the product from the database
        productRepository.deleteById(idToDelete);
    }

    @Override
    public Set<Product> listProductsByName(String productName) {

        return productRepository.findByNameContainingIgnoreCase(productName);
    }
}
