package com.ecommerce.spring5onlineshop.repositories;

import com.ecommerce.spring5onlineshop.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
}
