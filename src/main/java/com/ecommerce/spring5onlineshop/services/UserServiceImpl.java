package com.ecommerce.spring5onlineshop.services;

import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.converters.UserCommandToUser;
import com.ecommerce.spring5onlineshop.converters.UserToUserCommand;
import com.ecommerce.spring5onlineshop.model.Authority;
import com.ecommerce.spring5onlineshop.model.AuthorityType;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import com.ecommerce.spring5onlineshop.model.User;
import com.ecommerce.spring5onlineshop.repositories.ShoppingCartRepository;
import com.ecommerce.spring5onlineshop.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserCommandToUser userCommandToUser;
    private final UserToUserCommand userToUserCommand;

    public UserServiceImpl(UserRepository userRepository,
                           ShoppingCartRepository shoppingCartRepository,
                           UserCommandToUser userCommandToUser,
                           UserToUserCommand userToUserCommand) {
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
    }

    @Override
    @Transactional
    public UserCommand saveUserCommand(UserCommand command) {
        User detachedUser = userCommandToUser.convert(command);

        assert detachedUser != null;
        User savedUser = userRepository.save(detachedUser);
        log.debug("Saved user ID: " + savedUser.getId());
        return userToUserCommand.convert(savedUser);
    }

    @Override
    public User findById(Long l) {

        Optional<User> userOptional = userRepository.findById(l);

        if (userOptional.isPresent()) {
            return userOptional.get();
        }

        throw new RuntimeException("User not found");

    }

    @Override
    @Transactional
    public UserCommand findCommandByUsername(String username) {

        return userToUserCommand.convert(userRepository.getUserByUsername(username));
    }

    @Override
    public void setAuthority(UserCommand command, Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String grantedAuthority = authorities.iterator().next().getAuthority();

        command.setAuthorities(Set.of(new Authority(AuthorityType.valueOf(grantedAuthority))));
    }

    @Override
    public Long findShoppingCartIdByUsername(String username) {
        User user = userRepository.getUserByUsername(username);

        Optional<ShoppingCart> shoppingCartOptional =
                shoppingCartRepository.findShoppingCartById(user.getShoppingCart().getId());

        if (shoppingCartOptional.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOptional.get();

            return shoppingCart.getId();
        }

        throw new RuntimeException("Shopping cart not found");
    }
}
