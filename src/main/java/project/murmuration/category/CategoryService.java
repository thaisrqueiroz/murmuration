package project.murmuration.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.murmuration.category.dto.CategoryMapper;
import project.murmuration.category.dto.CategoryRequest;
import project.murmuration.category.dto.CategoryResponse;
import project.murmuration.exceptions.EntityNotFoundException;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryMapper::entityToDto).toList();
    }

    public Category getCategoryEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Category.class.getSimpleName(), "id", id.toString()));
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = getCategoryEntityById(id);
        return CategoryMapper.entityToDto(category);
    }

    public CategoryResponse addCategory(CategoryRequest categoryRequest){
        Category category = CategoryMapper.dtoToEntity(categoryRequest);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.entityToDto(savedCategory);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category updatedCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Category.class.getSimpleName(), "id", id.toString()));
        updatedCategory.setName(categoryRequest.name());
        Category savedCategory = categoryRepository.save(updatedCategory);
        return CategoryMapper.entityToDto(savedCategory);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Category.class.getSimpleName(), "id", id.toString()));
        categoryRepository.delete(category);
    }
}