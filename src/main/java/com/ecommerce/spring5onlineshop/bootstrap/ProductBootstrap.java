package com.ecommerce.spring5onlineshop.bootstrap;

import com.ecommerce.spring5onlineshop.model.*;
import com.ecommerce.spring5onlineshop.repositories.CategoryRepository;
import com.ecommerce.spring5onlineshop.repositories.ProductRepository;
import com.ecommerce.spring5onlineshop.repositories.ShoppingCartRepository;
import com.ecommerce.spring5onlineshop.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Component
public class ProductBootstrap implements ApplicationListener<ContextRefreshedEvent>{

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShoppingCartRepository shoppingCartRepository;

    public ProductBootstrap(CategoryRepository categoryRepository, ProductRepository productRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, ShoppingCartRepository shoppingCartRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public void onApplicationEvent(@NotNull ContextRefreshedEvent contextRefreshedEvent) {
        productRepository.saveAll(getProducts());
        userRepository.saveAll(getUsers());
        shoppingCartRepository.saveAll(getCarts());
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
        annaKareninaBookProduct.setImage(fileToByteArray("Anna_Karenina"));


        Product smartLedTv = new Product();
        smartLedTv.getCategories().add(electronicsCategory);
        smartLedTv.setName("LG Electronics 24LH4830-PU 24-Inch Smart LED TV (2016 Model)");
        smartLedTv.setPrice(111.99F);
        smartLedTv.setStock(3);
        smartLedTv.setDescription("The 24LH4830 PU Simple Smart TV. Features Simple Smart TV functionality. " +
                "Screen Share (Miracast & WiDi). Wide viewing angle screen. ENERGY STAR Qualified." +
                "Network File Browser Yes ; Google Dial Yes");
        smartLedTv.setImage(fileToByteArray("Smart_led_TV"));


        Product waterproofSkiJacket = new Product();
        waterproofSkiJacket.getCategories().add(clothingCategory);
        waterproofSkiJacket.setName("MOERDENG Men's Waterproof Ski Jacket Warm Winter Snow Coat Mountain" +
                " Windbreaker Hooded Raincoat");
        waterproofSkiJacket.setPrice(79.99F);
        waterproofSkiJacket.setStock(5);
        waterproofSkiJacket.setDescription("This jacket is made from new high quality polyester material, which is " +
                "waterproof, windproof, durable and stain repellent.\n" +
                "Special high-density fabric and coating, film composite process to obstruct the air intrusion " +
                "effectively and works well on windproof.\n" +
                "Ergonomic 3-structured cutting and fuzzy lining can provide enough warmth for outdoor life");
        waterproofSkiJacket.setImage(fileToByteArray("Winter_jacket"));


        products.add(annaKareninaBookProduct);
        products.add(smartLedTv);
        products.add(waterproofSkiJacket);

        return products;
    }

    private List<User> getUsers() {

        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword(passwordEncoder.encode("pass"));
        user1.setFirstName("Mark");
        user1.setLastName("Jackson");
        user1.setGender("Male");
        user1.setBirthDate("10.02.1986");
        user1.setAddress("Fifth Av., New York");
        user1.setPhone("0855-434-3233");
        user1.setEmail("mark.34@somewhere.com");
        user1.setShoppingCart(new ShoppingCart());
        user1.setAuthorities(Set.of(new Authority(AuthorityType.ROLE_ADMIN)));

        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword(passwordEncoder.encode("pass"));
        user2.setFirstName("Sarah");
        user2.setLastName("Smith");
        user2.setGender("Female");
        user2.setBirthDate("10.02.1994");
        user2.setAddress("Green Str., Chicago");
        user2.setPhone("0855-344-3211");
        user2.setEmail("sara.26@somewhere.com");
        user2.setShoppingCart(new ShoppingCart());
        user2.setAuthorities(Set.of(new Authority(AuthorityType.ROLE_USER)));

        users.add(user1);
        users.add(user2);

        return users;
    }


    private List<ShoppingCart> getCarts() {
        List<ShoppingCart> shoppingCarts = new ArrayList<>();

        ShoppingCart cart1 = new ShoppingCart();

        Iterable<Product> products = productRepository.findAll();

        Set<Product> productSet = new HashSet<>();

        products.forEach(productSet::add);

        cart1.setProducts(productSet);

        shoppingCarts.add(cart1);

        return shoppingCarts;
    }

    /**
     * Utility method for converting a local image
     * to a Byte array object
     *
     * @param fileName Name of the image
     * @return Byte[]
     */
    @Nullable
    private Byte[] fileToByteArray(String fileName) {

        File file = new File("src\\main\\resources\\static\\images\\" + fileName + ".jpg");

        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            Byte[] byteObject = new Byte[bytes.length];

            int i = 0;
            for (byte b : bytes) {
                byteObject[i++] = b;
            }

            return byteObject;
        } catch (IOException ex) {
            System.out.println("Could not convert file to a byte array" + ex.getMessage());
            ex.printStackTrace();
        }

        return null;
    }
}
