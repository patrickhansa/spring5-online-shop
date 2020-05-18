package com.ecommerce.spring5onlineshop.api.controllers;

import com.ecommerce.spring5onlineshop.api.mapper.ProductMapper;
import com.ecommerce.spring5onlineshop.api.model.ProductSetDTO;
import com.ecommerce.spring5onlineshop.commands.ShoppingCartCommand;
import com.ecommerce.spring5onlineshop.services.ShoppingCartService;
import com.ecommerce.spring5onlineshop.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shoppingCart")
public class RestShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final ProductMapper productMapper;

    public RestShoppingCartController(ShoppingCartService shoppingCartService,
                                      UserService userService,
                                      ProductMapper productMapper) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.productMapper = productMapper;
    }

    @RequestMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ProductSetDTO getProductsInShoppingCart(@PathVariable String username) {

        Long shoppingCartId = userService.findShoppingCartIdByUsername(username);

        return new ProductSetDTO(shoppingCartService.getProductsInShoppingCart(shoppingCartId)
                .stream()
                .map(productMapper::productToProductDTO)
                .collect(Collectors.toSet()));
    }

    @RequestMapping("/{username}/purchase")
    @ResponseStatus(HttpStatus.OK)
    public void placeOrder(@PathVariable String username) {

        Long shoppingCartId = userService.findShoppingCartIdByUsername(username);

        shoppingCartService.placeOrder(shoppingCartId);
    }

    @RequestMapping("/{username}/cartTotal")
    @ResponseStatus(HttpStatus.OK)
    public Float getShoppingCartPrice(@PathVariable String username) {

        return shoppingCartService.getShoppingCartPrice(username);
    }

    @PutMapping("/{username}/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProductToShoppingCart(@PathVariable String username, @PathVariable String productId) {

        Long shoppingCartId = userService.findShoppingCartIdByUsername(username);

        ShoppingCartCommand shoppingCartCommand =
                shoppingCartService.addProductToShoppingCart(Long.valueOf(productId), shoppingCartId);

        shoppingCartService.saveShoppingCartCommand(shoppingCartCommand);
    }

    @DeleteMapping("/{username}/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductInShoppingCart(@PathVariable String username, @PathVariable String productId) {

        Long shoppingCartId = userService.findShoppingCartIdByUsername(username);

        shoppingCartService.removeItemFromShoppingCart(shoppingCartId, Long.valueOf(productId));
    }
}
