package project.murmuration.dtos.offer;

import project.murmuration.dtos.user.UserResponse;

public record OfferResponse (
        Long id,
        String title,
        String description,
        String category,
        int price,
        UserResponse user,
        String location
){
}