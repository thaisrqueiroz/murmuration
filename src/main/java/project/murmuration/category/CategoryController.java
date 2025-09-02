package project.murmuration.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Category", description = "Category management endpoints")
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get all categories",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved list of categories"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                @ApiResponse(responseCode = "403", description = "Forbidden access"),
                @ApiResponse(responseCode = "404", description = "Category not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
             })
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    @Operation(summary = "Add a new category (Admin only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category added successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                    @ApiResponse(responseCode = "403", description = "Forbidden access"),
                    @ApiResponse(responseCode = "404", description = "Category not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })

    public ResponseEntity<CategoryResponse> addCategory(@RequestBody @Valid CategoryRequest newCategory) {
        CategoryResponse createdCategory = categoryService.addCategory(newCategory);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category (Admin only)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                    @ApiResponse(responseCode = "403", description = "Forbidden access"),
                    @ApiResponse(responseCode = "404", description = "Category not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequest updatedCategory) {
        CategoryResponse categoryResponse = categoryService.updateCategory(id, updatedCategory);
        return ResponseEntity.ok(categoryResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category (Admin only)",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                    @ApiResponse(responseCode = "403", description = "Forbidden access"),
                    @ApiResponse(responseCode = "404", description = "Category not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}