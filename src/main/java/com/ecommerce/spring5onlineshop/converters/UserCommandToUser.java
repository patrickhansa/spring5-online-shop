package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.model.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserCommandToUser implements Converter<UserCommand, User> {

    private final ShoppingCartCommandToShoppingCart shoppingCartConverter;

    public UserCommandToUser(ShoppingCartCommandToShoppingCart shoppingCartConverter) {
        this.shoppingCartConverter = shoppingCartConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public User convert(UserCommand source) {
        if (source == null) {
            return null;
        }

        final User user = new User();
        user.setId(source.getId());
        user.setUsername(source.getUsername());
        user.setPassword(source.getPassword());
        user.setEmail(source.getEmail());
        user.setFirstName(source.getFirstName());
        user.setLastName(source.getLastName());
        user.setPhone(source.getPhone());
        user.setGender(source.getGender());
        user.setBirthDate(source.getBirthDate());
        user.setAddress(source.getAddress());
        user.setShoppingCart(shoppingCartConverter.convert(source.getShoppingCart()));
        return user;
    }
}
