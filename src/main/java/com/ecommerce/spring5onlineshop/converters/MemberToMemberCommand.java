package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.MemberCommand;
import com.ecommerce.spring5onlineshop.model.Member;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MemberToMemberCommand implements Converter<Member, MemberCommand> {

    private final ShoppingCartToShoppingCartCommand shoppingCartConverter;

    public MemberToMemberCommand(ShoppingCartToShoppingCartCommand shoppingCartConverter) {
        this.shoppingCartConverter = shoppingCartConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public MemberCommand convert(Member source) {
        if (source == null) {
            return null;
        }

        final MemberCommand memberCommand = new MemberCommand();
        memberCommand.setId(source.getId());
        memberCommand.setUserName(source.getUserName());
        memberCommand.setPassword(source.getPassword());
        memberCommand.setEmail(source.getEmail());
        memberCommand.setFirstName(source.getFirstName());
        memberCommand.setLastName(source.getLastName());
        memberCommand.setPhone(source.getPhone());
        memberCommand.setGender(source.getGender());
        memberCommand.setBirthDate(source.getBirthDate());
        memberCommand.setAddress(source.getAddress());
        memberCommand.setShoppingCart(shoppingCartConverter.convert(source.getShoppingCart()));
        return memberCommand;
    }
}
