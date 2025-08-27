package project.murmuration.offer.dto;

import project.murmuration.category.dto.CategoryResponse;
import project.murmuration.user.dto.UserResponse;

public record OfferResponse (
        Long id,
        String title,
        String description,
        CategoryResponse category,
        int price,
        UserResponse user,
        String location,
        boolean isUnique
){
}