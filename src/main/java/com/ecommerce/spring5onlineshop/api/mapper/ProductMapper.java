package com.ecommerce.spring5onlineshop.api.mapper;

import com.ecommerce.spring5onlineshop.api.model.ProductDTO;
import com.ecommerce.spring5onlineshop.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ProductMapper extends BaseMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "product", target = "image", qualifiedByName = "convertByteImage")
    ProductDTO productToProductDTO(Product product);

    @Mapping(source = "image", target = "image", qualifiedByName = "convertStringToImage")
    Product productDTOToProduct(ProductDTO productDTO);
}
