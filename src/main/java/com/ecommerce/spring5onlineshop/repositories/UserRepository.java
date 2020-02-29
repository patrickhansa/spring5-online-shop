package com.ecommerce.spring5onlineshop.repositories;

import com.ecommerce.spring5onlineshop.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User getUserByUsername(String username);
}
