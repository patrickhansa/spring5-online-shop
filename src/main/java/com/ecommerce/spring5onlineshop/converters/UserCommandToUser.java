package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.ShoppingCartCommand;
import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.model.Authority;
import com.ecommerce.spring5onlineshop.model.AuthorityType;
import com.ecommerce.spring5onlineshop.model.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    private final ShoppingCartCommandToShoppingCart shoppingCartConverter;

    private PasswordEncoder passwordEncoder;

    public UserCommandToUser(ShoppingCartCommandToShoppingCart shoppingCartConverter, PasswordEncoder passwordEncoder) {
        this.shoppingCartConverter = shoppingCartConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Synchronized
    @Nullable
    @Override
    public User convert(UserCommand source) {

        final User user = new User();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setPassword(source.getPassword());
        user.setPassword(passwordEncoder.encode(source.getPassword()));
        user.setEmail(source.getEmail());
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setPhone(source.getPhone());
        user.setGender(source.getGender());
        user.setBirthDate(source.getBirthDate());
        user.setAddress(source.getAddress());

        // If the user does not have a shopping cart,
        // then create one for him. Otherwise, use the
        // one already assigned to him.
        if (source.getShoppingCart() == null) {
            user.setShoppingCart(shoppingCartConverter.convert(new ShoppingCartCommand()));
        } else {
            user.setShoppingCart(shoppingCartConverter.convert(source.getShoppingCart()));
        }

        // If it's a new member, give him a 'USER' authority.
        // Otherwise, keep the authority he already has.
        if (source.getAuthorities().isEmpty()) {
            user.setAuthorities(Set.of(new Authority(AuthorityType.ROLE_USER)));
        } else {
            user.setAuthorities(source.getAuthorities());
        }

        return user;
    }
}
