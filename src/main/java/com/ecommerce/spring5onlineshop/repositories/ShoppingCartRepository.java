package com.ecommerce.spring5onlineshop.repositories;

import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findShoppingCartById(Long id);
}
