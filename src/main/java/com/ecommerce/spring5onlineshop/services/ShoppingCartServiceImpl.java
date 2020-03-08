package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.commands.ShoppingCartCommand;
import com.ecommerce.spring5onlineshop.converters.ShoppingCartCommandToShoppingCart;
import com.ecommerce.spring5onlineshop.converters.ShoppingCartToShoppingCartCommand;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import com.ecommerce.spring5onlineshop.repositories.ProductRepository;
import com.ecommerce.spring5onlineshop.repositories.ShoppingCartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartCommandToShoppingCart shoppingCartCommandToShoppingCart;
    private final ShoppingCartToShoppingCartCommand shoppingCartToShoppingCartCommand;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    public ShoppingCartServiceImpl(ShoppingCartCommandToShoppingCart shoppingCartCommandToShoppingCart,
                                   ShoppingCartToShoppingCartCommand shoppingCartToShoppingCartCommand,
                                   ShoppingCartRepository shoppingCartRepository,
                                   ProductRepository productRepository,
                                   UserService userService) {
        this.shoppingCartCommandToShoppingCart = shoppingCartCommandToShoppingCart;
        this.shoppingCartToShoppingCartCommand = shoppingCartToShoppingCartCommand;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public ShoppingCartCommand saveShoppingCartCommand(ShoppingCartCommand command) {
        ShoppingCart detachedShoppingCart = shoppingCartCommandToShoppingCart.convert(command);

        assert detachedShoppingCart != null;
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(detachedShoppingCart);
        log.debug("Saved shopping cart ID: " + savedShoppingCart.getId());
        return shoppingCartToShoppingCartCommand.convert(savedShoppingCart);
    }

    @Override
    public ShoppingCartCommand addProductToShoppingCart(Long productId, Long shoppingCartId) {
        Optional<Product> productOptional = productRepository.findProductById(productId);
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findShoppingCartById(shoppingCartId);

        if (productOptional.isPresent() && shoppingCartOptional.isPresent()) {
            Product product = productOptional.get();
            ShoppingCart shoppingCart = shoppingCartOptional.get();

            log.debug("Add product with ID " + productId +
                    " to the shopping cart with ID " + shoppingCartId);
            shoppingCart.getProducts().add(product);

            return shoppingCartToShoppingCartCommand.convert(shoppingCart);
        } else {
            throw new RuntimeException("Expected product or shopping cart not found");
        }
    }

    @Override
    public Set<Product> getProductsInShoppingCart(Long shoppingCartId) {

        return productRepository.getProductsByShoppingCartId(shoppingCartId);
    }

    @Override
    public Float getShoppingCartPrice(String username) {
        Float sum = 0F;

        // Get shopping cart id
        Long shoppingCartId = userService.findShoppingCartIdByUsername(username);

        // Get the list of products in the shopping cart
        Set<Product> productSet = productRepository.getProductsByShoppingCartId(shoppingCartId);

        // Calculate the sum of all the product prices
        for (Product product : productSet) {
            sum += product.getPrice();
        }

        return sum;
    }

    @Override
    @Transactional
    public void placeOrder(Long shoppingCartId) {
        // Get the products in the shopping cart
        Set<Product> productsInShoppingCart = getProductsInShoppingCart(shoppingCartId);

        // Get the products in the database
        Set<Product> productsInDb = new HashSet<>();
        productRepository.findAll().iterator().forEachRemaining(productsInDb::add);

        // Subtract the stock of the items in the database
        // from the ones that were ordered
        originalSet: for (Product productInDb : productsInDb) {
            for (Product productInShoppingCart : productsInShoppingCart) {
                if (productInDb.getId().equals(productInShoppingCart.getId())) {
                    // Hard code the -1 until the functionality to
                    // choose the quantity to order is implemented
                    productInDb.setStock(productInDb.getStock() - 1);
                    continue originalSet;
                }
            }
        }

        productRepository.saveAll(productsInDb);
    }

    @Override
    @Transactional
    public void removeItemFromShoppingCart(Long shoppingCartId, Long productId) {
        // Get the products in the shopping cart
        Set<Product> productsInShoppingCart = productRepository.getProductsByShoppingCartId(shoppingCartId);

        // Remove the product from the shopping cart
        productsInShoppingCart.removeIf(product -> product.getId().equals(productId));

        // Save the shopping cart into the DB
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findShoppingCartById(shoppingCartId);
        if (shoppingCartOptional.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOptional.get();

            shoppingCart.setProducts(productsInShoppingCart);

            shoppingCartRepository.save(shoppingCart);
        }
    }
}