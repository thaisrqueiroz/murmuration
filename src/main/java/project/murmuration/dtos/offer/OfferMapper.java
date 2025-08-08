package project.murmuration.dtos.offer;

import project.murmuration.dtos.user.UserResponse;
import project.murmuration.dtos.user.UserMapper;
import project.murmuration.models.Category;
import project.murmuration.models.Offer;

public class OfferMapper {
    static Offer dtoToEntity(OfferRequest dto, Category category){
        return new Offer(dto.title(), dto.description(), category, dto.price(), dto.location());
    }

    static OfferResponse entityToDto(Offer offer){
        UserResponse userResponse = offer.getUser() != null
                ? UserMapper.entityToDto(offer.getUser())
                : null;
        String category = (offer.getCategory() != null) ? offer.getCategory().getName() : null;
        return new OfferResponse(offer.getId(), offer.getTitle(), offer.getDescription(), category, offer.getPrice(), userResponse, offer.getLocation());
    }
}