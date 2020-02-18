package com.ecommerce.spring5onlineshop.bootstrap;

import com.ecommerce.spring5onlineshop.model.Category;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.repositories.CategoryRepository;
import com.ecommerce.spring5onlineshop.repositories.ProductRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductBootstrap(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        productRepository.saveAll(getProducts());
    }

    private List<Product> getProducts() {

        List<Product> products = new ArrayList<>();

        // Get categories
        Optional<Category> bookCategoryOptional = categoryRepository.findByDescription("Book");
        if (!bookCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected category not found");
        }

        Optional<Category> electronicsCategoryOptional = categoryRepository.findByDescription("Electronics");
        if (!electronicsCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected category not found");
        }

        Optional<Category> clothingCategoryOptional = categoryRepository.findByDescription("Clothing");
        if (!clothingCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected category not found");
        }

        Category bookCategory = bookCategoryOptional.get();
        Category electronicsCategory = electronicsCategoryOptional.get();
        Category clothingCategory = clothingCategoryOptional.get();

        Product annaKareninaBookProduct = new Product();
        annaKareninaBookProduct.getCategories().add(bookCategory);
        annaKareninaBookProduct.setName("Anna Karenina Paperback – Deckle Edge");
        annaKareninaBookProduct.setPrice(12.89F);
        annaKareninaBookProduct.setStock(2);
        annaKareninaBookProduct.setDescription("Described by William Faulkner as the best novel ever written and " +
                "by Fyodor Dostoevsky as “flawless,” Anna Karenina tells of the doomed love affair between the " +
                "sensuous and rebellious Anna and the dashing officer, Count Vronsky. Tragedy unfolds as " +
                "Anna rejects her passionless marriage and thereby exposes herself to the hypocrisies of society. " +
                "Set against a vast and richly textured canvas of nineteenth-century Russia, the novel's seven " +
                "major characters create a dynamic imbalance, playing out the contrasts of city and country life " +
                "and all the variations on love and family happiness.\n");

        Product smartLedTv = new Product();
        smartLedTv.getCategories().add(electronicsCategory);
        smartLedTv.setName("LG Electronics 24LH4830-PU 24-Inch Smart LED TV (2016 Model)");
        smartLedTv.setPrice(111.99F);
        smartLedTv.setStock(3);
        smartLedTv.setDescription("The 24LH4830 PU Simple Smart TV. Features Simple Smart TV functionality. " +
                "Screen Share (Miracast & WiDi). Wide viewing angle screen. ENERGY STAR Qualified." +
                "Network File Browser Yes ; Google Dial Yes");

        Product waterproofSkiJacket = new Product();
        waterproofSkiJacket.getCategories().add(clothingCategory);
        waterproofSkiJacket.setName("MOERDENG Men's Waterproof Ski Jacket Warm Winter Snow Coat Mountain" +
                " Windbreaker Hooded Raincoat");
        waterproofSkiJacket.setPrice(79.99F);
        waterproofSkiJacket.setStock(5);
        waterproofSkiJacket.setDescription("This jacket is made from new high quality polyester material, which is " +
                "waterproof, windproof, durable and stain repellent.\n" +
                "Speciall high-density fabric and coating, film composite process to obstructe the air intrusion " +
                "effectively and works well on windproof.\n" +
                "Ergonomic 3-structured cutting and fuzzy lining can provide enough warmth for outdoor life");

        products.add(annaKareninaBookProduct);
        products.add(smartLedTv);
        products.add(waterproofSkiJacket);

        return products;
    }
}
