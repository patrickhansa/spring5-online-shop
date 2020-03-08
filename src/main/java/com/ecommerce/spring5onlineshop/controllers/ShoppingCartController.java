package com.ecommerce.spring5onlineshop.controllers;

import com.ecommerce.spring5onlineshop.commands.ShoppingCartCommand;
import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.services.ShoppingCartService;
import com.ecommerce.spring5onlineshop.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @RequestMapping("shoppingCart/view")
    public String showShoppingCart(Authentication authentication, Model model) {

        UserDetails user = (UserDetails) authentication.getPrincipal();

        Long shoppingCartId = userService.findShoppingCartIdByUsername(user.getUsername());

        model.addAttribute("products", shoppingCartService.getProductsInShoppingCart(shoppingCartId));

        return "shoppingCart/show";
    }

    @RequestMapping("shoppingCart/checkout")
    public String performCheckout(Authentication authentication, Model model) {

        UserDetails user = (UserDetails) authentication.getPrincipal();

        model.addAttribute("user", userService.findCommandByUsername(user.getUsername()));
        model.addAttribute("price", shoppingCartService.getShoppingCartPrice(user.getUsername()));

        return "shoppingCart/checkoutView";
    }

    @RequestMapping("shoppingCart/purchase")
    public String placeOrder(Authentication authentication) {

        UserDetails user = (UserDetails) authentication.getPrincipal();

        Long shoppingCartId = userService.findShoppingCartIdByUsername(user.getUsername());

        shoppingCartService.placeOrder(shoppingCartId);

        return "shoppingCart/purchasedView";
    }

    @RequestMapping("shoppingCart/product/{id}/remove")
    public String removeItemFromShoppingCart(@PathVariable String id, Authentication authentication) {

        UserDetails user = (UserDetails) authentication.getPrincipal();

        Long shoppingCartId = userService.findShoppingCartIdByUsername(user.getUsername());

        shoppingCartService.removeItemFromShoppingCart(shoppingCartId, Long.valueOf(id));

        return "shoppingCart/show";
    }

    @RequestMapping("user/changeAddress")
    public String editUser(Authentication authentication, Model model) {

        UserDetails user = (UserDetails) authentication.getPrincipal();

        model.addAttribute("user", userService.findCommandByUsername(user.getUsername()));

        return "shoppingCart/editDeliveryAddressForm";
    }

    @PostMapping("user/changeAddress")
    public String changeAddress(@ModelAttribute UserCommand command, Authentication authentication, Model model) {

        UserDetails user = (UserDetails) authentication.getPrincipal();

        userService.setAuthority(command, authentication);
        UserCommand savedCommand = userService.saveUserCommand(command);

        model.addAttribute("user", savedCommand);
        model.addAttribute("price", shoppingCartService.getShoppingCartPrice(user.getUsername()));

        return "shoppingCart/checkoutView";
    }
}