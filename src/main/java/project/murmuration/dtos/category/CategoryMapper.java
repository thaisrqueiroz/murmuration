package project.murmuration.dtos.category;

import project.murmuration.models.Category;
import java.util.List;

public class CategoryMapper {
    public static Category dtoToEntity (CategoryRequest dto){
        return new Category(dto.name());
    }

    public static CategoryResponse entityToDto(Category category){
        return new CategoryResponse(category.getId(), category.getName());
    }

    public static List<CategoryResponse> entitiesToDtos(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::entityToDto)
                .toList();
    }
}