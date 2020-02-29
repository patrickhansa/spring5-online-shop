package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.model.ShopUserDetails;
import com.ecommerce.spring5onlineshop.model.User;
import com.ecommerce.spring5onlineshop.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.getUserByUsername(username);

        if (user != null) {
            return new ShopUserDetails(user);
        }

        throw new UsernameNotFoundException("User '" + username + "' not found");
    }
}
