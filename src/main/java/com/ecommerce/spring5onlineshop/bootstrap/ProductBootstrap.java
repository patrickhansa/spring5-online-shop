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
        if (categoryRepository.count() == 0) {
            categoryRepository.saveAll(getCategories());
        }
        if (productRepository.count() == 0) {
            productRepository.saveAll(getProducts());
        }
        if (userRepository.count() == 0) {
            userRepository.saveAll(getUsers());
        }
        if (shoppingCartRepository.count() == 0) {
            shoppingCartRepository.saveAll(getCarts());
        }
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

        Product brothersKaramazovProduct = new Product();
        brothersKaramazovProduct.getCategories().add(bookCategory);
        brothersKaramazovProduct.setName("The Brothers Karamazov");
        brothersKaramazovProduct.setPrice(5.88F);
        brothersKaramazovProduct.setStock(3);
        brothersKaramazovProduct.setDescription("The Brothers Karamazov is a murder mystery, a courtroom drama, and " +
                "an exploration of erotic rivalry in a series of triangular love affairs involving the “wicked and" +
                " sentimental” Fyodor Pavlovich Karamazov and his three sons―the impulsive and sensual Dmitri;" +
                " the coldly rational Ivan; and the healthy, red-cheeked young novice Alyosha. Through the " +
                "gripping events of their story, Dostoevsky portrays the whole of Russian life, is social and " +
                "spiritual striving, in what was both the golden age and a tragic turning point in Russian culture.\n" +
                "This award-winning translation by Richard Pevear and Larissa Volokhonsky remains true to the verbal\n" +
                "inventiveness of Dostoevsky’s prose, preserving the multiple voices, the humor, and the surprising " +
                "modernity of the original. It is an achievement worthy of Dostoevsky’s last and greatest novel.\n");
        brothersKaramazovProduct.setImage(fileToByteArray("Brothers_Karamazov"));

        Product thePlagueProduct = new Product();
        thePlagueProduct.getCategories().add(bookCategory);
        thePlagueProduct.setName("The plague");
        thePlagueProduct.setPrice(11.01F);
        thePlagueProduct.setStock(3);
        thePlagueProduct.setDescription("A haunting tale of human resilience in the face of " +
                "unrelieved horror, Camus' novel about a bubonic plague ravaging the people of a " +
                "North African coastal town is a classic of twentieth-century literature.");
        thePlagueProduct.setImage(fileToByteArray("The_plague"));

        Product smartLedTv = new Product();
        smartLedTv.getCategories().add(electronicsCategory);
        smartLedTv.setName("LG Electronics 24LH4830-PU 24-Inch Smart LED TV (2016 Model)");
        smartLedTv.setPrice(111.99F);
        smartLedTv.setStock(3);
        smartLedTv.setDescription("The 24LH4830 PU Simple Smart TV. Features Simple Smart TV functionality. " +
                "Screen Share (Miracast & WiDi). Wide viewing angle screen. ENERGY STAR Qualified." +
                "Network File Browser Yes ; Google Dial Yes");
        smartLedTv.setImage(fileToByteArray("Smart_led_TV"));

        Product noiseCancellingHeadphonesProduct = new Product();
        noiseCancellingHeadphonesProduct.getCategories().add(electronicsCategory);
        noiseCancellingHeadphonesProduct.setName("Noise Cancelling Headphones Real Over Ear,Wireless Lightweight " +
                "Srhythm Durable Foldable Deep Bass Hi-Fi Stereo Bluetooth Headset with Mic and Wire for TV, " +
                "PC, Cell Phone- Low Latency");
        noiseCancellingHeadphonesProduct.setPrice(59.99F);
        noiseCancellingHeadphonesProduct.setStock(1);
        noiseCancellingHeadphonesProduct.setDescription("Active Noise Cancellation Technology (ANC). Professional " +
                "advanced noise cancellation technology,effectively cancel 85% machine noise such as " +
                "airplane/bus/factory/office noise, etc.(pls kindly note:it's not 100% noise cancellation, " +
                "but it will quell much background noise.)so you can focus on what are you doing. " +
                "ANC function works well in both wired and wireless mode.NC25 noise cancelling " +
                "headphones would be the best model in the same level of price.\n Ultimate Fit & Comfort - " +
                "Exclusive Designs of Air Pressure Balance.Multi-level adjustable headband to get the best " +
                "suitable size for you.To reduce sound wave pressure of active noise reduction,to feel much " +
                "more comfortable to wear than all other brands.");
        noiseCancellingHeadphonesProduct.setImage(fileToByteArray("Headphones"));

        Product whiteNoiseMachineProduct = new Product();
        whiteNoiseMachineProduct.getCategories().add(electronicsCategory);
        whiteNoiseMachineProduct.setName("Adaptive Sound Technologies LectroFan High Fidelity White Noise Sound " +
                "Machine with 20 Unique Non-Looping Fan and White Noise Sounds and Sleep Timer");
        whiteNoiseMachineProduct.setPrice(37.95F);
        whiteNoiseMachineProduct.setStock(4);
        whiteNoiseMachineProduct.setDescription("Provides ten fan sounds and ten ambient noise variations, including " +
                "white noise, pink noise and brown noise\n Helps mask disruptive environmental noises so you or your " +
                "little one can fall asleep with ease. 12 inch cord length\n Safe, solid-state design is powered by" +
                " AC or USB and dynamically creates unique, non-repeating sounds with no moving parts. " +
                "Power is 110V AC, USB\n Precise volume control allows you to set the perfect level for your unique " +
                "environment. 1dB increment control for 10x quieter –10x louder than fan machines");
        whiteNoiseMachineProduct.setImage(fileToByteArray("White_noise"));

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

        Product underArmourCapProduct = new Product();
        underArmourCapProduct.getCategories().add(clothingCategory);
        underArmourCapProduct.setName("Under Armour Men's Blitzing 3.0 Cap");
        underArmourCapProduct.setPrice(24.99F);
        underArmourCapProduct.setStock(5);
        underArmourCapProduct.setDescription("UA Classic Fit features a pre-curved visor & structured front panels " +
                "that maintain shape with a low profile fit\n Front panel backed with foam padding for added comfort\n " +
                "UA Microthread fabric uses re-engineered fibers designed to give superior stretch & breathability\n " +
                "Built-in HeatGear sweatband wicks away sweat to keep you cool & dry\n Stretch construction provides" +
                " a comfortable fit");
        underArmourCapProduct.setImage(fileToByteArray("Cap"));

        Product glovesProduct = new Product();
        glovesProduct.getCategories().add(clothingCategory);
        glovesProduct.setName("Coolibar UPF 50+ Men's Women's Gannett UV Gloves - Sun Protective");
        glovesProduct.setPrice(32.00F);
        glovesProduct.setStock(5);
        glovesProduct.setDescription("UCmax™ fabric: A blend of breathable micro mesh and 4-way stretch fabric " +
                "engineered with our Cooltect™ technology to keep your skin cool, dry and works smart to " +
                "block UVA/UVB radiation while feeling soft and lightweight\n Touchscreen compatibility makes " +
                "for easy convenience; Silicone print on palm and fingers for added grip and dexterity\n Length of" +
                " glove (from top of middle finger to cuff edge - size medium): 9.4 inches; Easy care: machine wash," +
                " tumble dry; Imported\n Recommended for achieving Ultimate Coverage in Sun Protection for light" +
                " activities like driving, relaxing, or walking outdoors wearing UPF 50+ clothing");
        glovesProduct.setImage(fileToByteArray("Gloves"));

        products.add(annaKareninaBookProduct);
        products.add(brothersKaramazovProduct);
        products.add(thePlagueProduct);

        products.add(smartLedTv);
        products.add(noiseCancellingHeadphonesProduct);
        products.add(whiteNoiseMachineProduct);

        products.add(waterproofSkiJacket);
        products.add(underArmourCapProduct);
        products.add(glovesProduct);

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

    private List<Category> getCategories() {
        Category bookCategory = Category.builder().description("Book").build();
        Category electronicsCategory = Category.builder().description("Electronics").build();
        Category clothingCategory = Category.builder().description("Clothing").build();

        return new ArrayList<>(List.of(bookCategory, electronicsCategory, clothingCategory));
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
