package main.mappers;

import main.domain.Category;
import main.model.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

    CategoryDTO categoryToDto(Category category);
}
