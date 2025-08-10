package project.murmuration.offer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OfferRequest (
        @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
        String title,

        String description,

        @NotBlank(message = "Category id is required")
        Long categoryId,

        @NotNull(message = "Price is required")
        int price,

        @Size(min = 3, max = 50, message = "Location must be between 3 and 50 characters")
        String location
){
}