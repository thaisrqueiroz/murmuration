package project.murmuration.dtos.offer;

import project.murmuration.dtos.user.UserResponse;
import project.murmuration.models.Category;
import project.murmuration.models.User;

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