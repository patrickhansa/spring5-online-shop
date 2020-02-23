package com.ecommerce.spring5onlineshop.controllers;

import com.ecommerce.spring5onlineshop.commands.ProductCommand;
import com.ecommerce.spring5onlineshop.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/product/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("product", productService.findById(Long.valueOf(id)));

        return "product/show";
    }

    @RequestMapping("product/new")
    public String newProduct(Model model) {

        model.addAttribute("product", new ProductCommand());

        return "product/productForm";
    }

    @PostMapping
    @RequestMapping("product")
    public String saveOrUpdate(@ModelAttribute ProductCommand command) {

        ProductCommand savedCommand = productService.saveProductCommand(command);

        return "redirect:/product/" + savedCommand.getId() + "/show";
    }
}
