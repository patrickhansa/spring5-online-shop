package com.ecommerce.spring5onlineshop.repositories;

import com.ecommerce.spring5onlineshop.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends CrudRepository<Product, Long> {
     String SELECT_PRODUCTS_BY_SHOPPING_CART_ID = "select * from product inner join " +
            "shopping_cart_products as scp on (product.id = scp.products_id) where(scp.shopping_cart_id = ?1)";

    Optional<Product> findProductById(Long id);

    @Query(value = SELECT_PRODUCTS_BY_SHOPPING_CART_ID, nativeQuery = true)
    Set<Product> getProductsByShoppingCartId(Long id);

    Set<Product> findByNameContainingIgnoreCase(String productName);
}
