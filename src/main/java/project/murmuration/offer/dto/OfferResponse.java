package project.murmuration.offer.dto;

import project.murmuration.user.dto.UserResponse;
import project.murmuration.category.Category;

public record OfferResponse (
        Long id,
        String title,
        String description,
        Category category,
        int price,
        UserResponse user,
        String location
){
}