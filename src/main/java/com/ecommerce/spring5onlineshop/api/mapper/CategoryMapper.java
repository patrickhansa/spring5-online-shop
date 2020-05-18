package com.ecommerce.spring5onlineshop.api.mapper;

import com.ecommerce.spring5onlineshop.api.model.CategoryDTO;
import com.ecommerce.spring5onlineshop.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);

    @Mapping(source = "", target = "id")
    Category categoryDTOToCategory(CategoryDTO categoryDTO);
}
