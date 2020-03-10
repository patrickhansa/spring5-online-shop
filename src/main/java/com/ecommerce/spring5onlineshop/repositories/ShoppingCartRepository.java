package com.ecommerce.spring5onlineshop.repositories;

import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
    String SELECT_SHOPPING_CARTS_BY_PRODUCT_ID = "select * from shopping_cart inner join " +
            "shopping_cart_products as scp on (shopping_cart.id = scp.shopping_cart_id) where (scp.products_id = ?1)";

    Optional<ShoppingCart> findShoppingCartById(Long id);

    @Query(value = SELECT_SHOPPING_CARTS_BY_PRODUCT_ID, nativeQuery = true)
    Set<ShoppingCart> getShoppingCartsByProductId(Long id);
}
