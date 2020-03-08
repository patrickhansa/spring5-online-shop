package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.commands.ProductCommand;
import com.ecommerce.spring5onlineshop.converters.ProductCommandToProduct;
import com.ecommerce.spring5onlineshop.converters.ProductToProductCommand;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCommandToProduct productCommandToProduct;
    private final ProductToProductCommand productToProductCommand;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductCommandToProduct productCommandToProduct,
                              ProductToProductCommand productToProductCommand) {
        this.productRepository = productRepository;
        this.productCommandToProduct = productCommandToProduct;
        this.productToProductCommand = productToProductCommand;
    }

    @Override
    public Set<Product> getProducts() {
        Set<Product> productSet = new HashSet<>();
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
        productRepository.deleteById(idToDelete);
    }
}
