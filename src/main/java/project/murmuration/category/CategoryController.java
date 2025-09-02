package project.murmuration.category;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.murmuration.category.dto.CategoryRequest;
import project.murmuration.category.dto.CategoryResponse;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/categories")
@RestController
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody @Valid CategoryRequest newCategory) {
        CategoryResponse createdCategory = categoryService.addCategory(newCategory);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequest updatedCategory) {
        CategoryResponse categoryResponse = categoryService.updateCategory(id, updatedCategory);
        return ResponseEntity.ok(categoryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}