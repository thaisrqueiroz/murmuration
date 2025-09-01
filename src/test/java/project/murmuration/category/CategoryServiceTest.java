package project.murmuration.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.murmuration.category.dto.CategoryRequest;
import project.murmuration.category.dto.CategoryResponse;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryResponse categoryResponse;
    private CategoryRequest categoryRequest;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Food");

        categoryRequest = new CategoryRequest("Food");
        categoryResponse = new CategoryResponse(1L, "Food");
    }

    @Test
    @DisplayName("Should return all categories")
    void getAllCategories_whenCategoriesExist_returnsListOfCategories() {
        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Clothes");

        when(categoryRepository.findAll()).thenReturn(List.of(category, category2));

        List<CategoryResponse> result = categoryService.getAllCategories();

        assert(result.size() == 2);
        assert(result.get(0).equals(categoryResponse));
        assert(result.get(1).equals(new CategoryResponse(2L, "Clothes")));
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return a category by id")
    void getCategoryById_whenCategoryExists_returnsCategoryResponse() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryResponse result = categoryService.getCategoryById(1L);

        assert(result.equals(categoryResponse));
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should add a category")
    void addCategory_whenValidRequest_returnsCategoryResponse() {
        when(categoryRepository.existsByName(categoryRequest.name())).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponse result = categoryService.addCategory(categoryRequest);

        assert(result.equals(categoryResponse));
        verify(categoryRepository, times(1)).existsByName(categoryRequest.name());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Should not add a category with existing name")
    void addCategory_whenCategoryNameExists_throwsException() {
        when(categoryRepository.existsByName(categoryRequest.name())).thenReturn(true);

        try {
            categoryService.addCategory(categoryRequest);
            assert false;
        } catch (Exception exception) {
            assert exception.getMessage().contains("already exists");
        }

        verify(categoryRepository, times(1)).existsByName(categoryRequest.name());
        verify(categoryRepository, times(0)).save(any(Category.class));
    }

    @Test
    @DisplayName("Should update acategory")
    void updateCategory_whenCategoryExists_returnsCategoryResponse() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponse result = categoryService.updateCategory(1L, categoryRequest);

        assert(result.equals(categoryResponse));
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Should delete a category")
    void deleteCategory_whenCategoryExists_deletesCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).delete(category);
    }

}
