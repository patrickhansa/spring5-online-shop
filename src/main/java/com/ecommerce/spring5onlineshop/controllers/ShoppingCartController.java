package com.ecommerce.spring5onlineshop.controllers;

import com.ecommerce.spring5onlineshop.commands.ShoppingCartCommand;
import com.ecommerce.spring5onlineshop.services.ShoppingCartService;
import com.ecommerce.spring5onlineshop.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShoppingCartController {

    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(UserService userService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @RequestMapping("product/{id}/saveToCart")
    public String saveProductToShoppingCart(@PathVariable String id, Authentication authentication) {

        UserDetails user = (UserDetails) authentication.getPrincipal();

        Long shoppingCartId = userService.findShoppingCartIdByUsername(user.getUsername());

        ShoppingCartCommand shoppingCartCommand =
                shoppingCartService.addProductToShoppingCart(Long.valueOf(id), shoppingCartId);

        shoppingCartService.saveShoppingCartCommand(shoppingCartCommand);

        return "shoppingCart/savedToCart";
    }
}
