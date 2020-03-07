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

import java.util.Optional;

@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    ShoppingCartCommandToShoppingCart shoppingCartCommandToShoppingCart;
    ShoppingCartToShoppingCartCommand shoppingCartToShoppingCartCommand;
    ShoppingCartRepository shoppingCartRepository;
    ProductRepository productRepository;

    public ShoppingCartServiceImpl(ShoppingCartCommandToShoppingCart shoppingCartCommandToShoppingCart,
                                   ShoppingCartToShoppingCartCommand shoppingCartToShoppingCartCommand,
                                   ShoppingCartRepository shoppingCartRepository,
                                   ProductRepository productRepository) {
        this.shoppingCartCommandToShoppingCart = shoppingCartCommandToShoppingCart;
        this.shoppingCartToShoppingCartCommand = shoppingCartToShoppingCartCommand;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
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
}
