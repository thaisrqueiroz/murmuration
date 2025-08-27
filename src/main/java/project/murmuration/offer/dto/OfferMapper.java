package project.murmuration.offer.dto;

import project.murmuration.category.dto.CategoryMapper;
import project.murmuration.category.dto.CategoryResponse;
import project.murmuration.user.dto.UserResponse;
import project.murmuration.user.dto.UserMapper;
import project.murmuration.category.Category;
import project.murmuration.offer.Offer;
import project.murmuration.user.User;

public class OfferMapper {
    public static Offer dtoToEntity(OfferRequest dto, Category category, User user){
        return new Offer(dto.title(), dto.description(), category, dto.price(), dto.location(), user, dto.isUnique());
    }

    public static OfferResponse entityToDto(Offer offer){
        UserResponse userResponse = offer.getUser() != null
                ? UserMapper.entityToDto(offer.getUser())
                : null;
        CategoryResponse categoryResponse = offer.getCategory() != null
                ? CategoryMapper.entityToDto(offer.getCategory())
                : null;
        return new OfferResponse(offer.getId(), offer.getTitle(), offer.getDescription(), categoryResponse, offer.getPrice(), userResponse, offer.getLocation(), offer.isUnique());
    }
}