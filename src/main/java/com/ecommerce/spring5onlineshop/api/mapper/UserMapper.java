package com.ecommerce.spring5onlineshop.api.mapper;

import com.ecommerce.spring5onlineshop.api.model.ProductDTO;
import com.ecommerce.spring5onlineshop.api.model.UserDTO;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends BaseMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "authorities", target = "authorityDTOSet")
    @Mapping(source = "shoppingCart", target = "shoppingCartDTO", qualifiedByName = "shoppingCartToShoppingCartDTO")
    UserDTO userToUserDTO(User user);

    @Mapping(source = "product", target = "image", qualifiedByName = "convertByteImage")
    ProductDTO productToProductDTO(Product product);

    @Mapping(source = "authorityDTOSet", target = "authorities")
    @Mapping(source = "shoppingCartDTO", target = "shoppingCart", qualifiedByName = "shoppingCartDTOToShoppingCart")
    User userDTOToUser(UserDTO userDTO);

    @Mapping(source = "image", target = "image", qualifiedByName = "convertStringToImage")
    Product productDTOToProduct(ProductDTO productDTO);
}
