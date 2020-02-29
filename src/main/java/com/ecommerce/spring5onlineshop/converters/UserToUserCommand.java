package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.UserCommand;
import com.ecommerce.spring5onlineshop.model.User;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserToUserCommand implements Converter<User, UserCommand> {

    private final ShoppingCartToShoppingCartCommand shoppingCartConverter;

    public UserToUserCommand(ShoppingCartToShoppingCartCommand shoppingCartConverter) {
        this.shoppingCartConverter = shoppingCartConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public UserCommand convert(User source) {
        if (source == null) {
            return null;
        }

        final UserCommand userCommand = new UserCommand();
        userCommand.setId(source.getId());
        userCommand.setUsername(source.getUsername());
        userCommand.setPassword(source.getPassword());
        userCommand.setEmail(source.getEmail());
        userCommand.setFirstName(source.getFirstName());
        userCommand.setLastName(source.getLastName());
        userCommand.setPhone(source.getPhone());
        userCommand.setGender(source.getGender());
        userCommand.setBirthDate(source.getBirthDate());
        userCommand.setAddress(source.getAddress());
        userCommand.setShoppingCart(shoppingCartConverter.convert(source.getShoppingCart()));
        return userCommand;
    }
}
