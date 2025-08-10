package project.murmuration.offer.dto;

import project.murmuration.user.dto.UserResponse;
import project.murmuration.user.dto.UserMapper;
import project.murmuration.category.Category;
import project.murmuration.offer.Offer;
import project.murmuration.user.User;

public class OfferMapper {
    static Offer dtoToEntity(OfferRequest dto, Category category, User user){
        return new Offer(dto.title(), dto.description(), category, dto.price(), dto.location(), user);
    }

    static OfferResponse entityToDto(Offer offer, Category category){
        UserResponse userResponse = offer.getUser() != null
                ? UserMapper.entityToDto(offer.getUser())
                : null;
        return new OfferResponse(offer.getId(), offer.getTitle(), offer.getDescription(), category, offer.getPrice(), userResponse, offer.getLocation());
    }
}