package com.ecommerce.spring5onlineshop.api.mapper;

import com.ecommerce.spring5onlineshop.api.model.ProductDTO;
import com.ecommerce.spring5onlineshop.api.model.ShoppingCartDTO;
import com.ecommerce.spring5onlineshop.model.Product;
import com.ecommerce.spring5onlineshop.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShoppingCartMapper extends BaseMapper{

    ShoppingCartMapper INSTANCE = Mappers.getMapper(ShoppingCartMapper.class);

    ShoppingCartDTO shoppingCartToShoppingCartDTO(ShoppingCart shoppingCart);

    @Mapping(source = "product", target = "image", qualifiedByName = "convertByteImage")
    ProductDTO productToProductDTO(Product product);

    ShoppingCart shoppingCartDTOToShoppingCart(ShoppingCartDTO shoppingCartDTO);

    @Mapping(source = "image", target = "image", qualifiedByName = "convertStringToImage")
    Product productDTOToProduct(ProductDTO productDTO);
}
