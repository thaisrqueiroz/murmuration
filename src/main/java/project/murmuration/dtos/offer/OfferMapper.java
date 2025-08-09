package project.murmuration.dtos.offer;

import project.murmuration.dtos.user.UserResponse;
import project.murmuration.dtos.user.UserMapper;
import project.murmuration.models.Category;
import project.murmuration.models.Offer;
import project.murmuration.models.User;

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