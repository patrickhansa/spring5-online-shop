package com.ecommerce.spring5onlineshop.converters;

import com.ecommerce.spring5onlineshop.commands.MemberCommand;
import com.ecommerce.spring5onlineshop.model.Member;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class MemberCommandToMember implements Converter<MemberCommand, Member> {

    private final ShoppingCartCommandToShoppingCart shoppingCartConverter;

    public MemberCommandToMember(ShoppingCartCommandToShoppingCart shoppingCartConverter) {
        this.shoppingCartConverter = shoppingCartConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Member convert(MemberCommand source) {
        if (source == null) {
            return null;
        }

        final Member member = new Member();
        member.setId(source.getId());
        member.setUserName(source.getUserName());
        member.setPassword(source.getPassword());
        member.setEmail(source.getEmail());
        member.setFirstName(source.getFirstName());
        member.setLastName(source.getLastName());
        member.setPhone(source.getPhone());
        member.setGender(source.getGender());
        member.setBirthDate(source.getBirthDate());
        member.setAddress(source.getAddress());
        member.setShoppingCart(shoppingCartConverter.convert(source.getShoppingCart()));
        return member;
    }
}
