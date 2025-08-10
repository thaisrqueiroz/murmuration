package project.murmuration.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50, message = "Category name must be between 2 and 50 characters")
        String name
) {
}