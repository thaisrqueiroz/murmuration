package project.murmuration.offer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.murmuration.category.Category;
import project.murmuration.exceptions.EntityNotFoundException;
import project.murmuration.offer.dto.OfferMapper;
import project.murmuration.offer.dto.OfferRequest;
import project.murmuration.offer.dto.OfferResponse;
import project.murmuration.user.User;

import java.util.List;

@AllArgsConstructor
@Service
public class OfferService {
    public final OfferRepository offerRepository;

    public List<OfferResponse> getAllOffers() {
        List<Offer> offers = offerRepository.findAll();
        return offers.stream().map(OfferMapper::entityToDto).toList();
    }

    public Offer getOfferById(Long id) {
        return offerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Offer.class.getSimpleName(), "id", id.toString()));
    }

    public OfferResponse getOfferResponseById(Long id) {
        Offer offer = getOfferById(id);
        return OfferMapper.entityToDto(offer);
    }

    public OfferResponse addOffer(OfferRequest offerRequest, Category category, User user) {
        Offer newOffer = OfferMapper.dtoToEntity(offerRequest, category, user);
        Offer savedOffer = offerRepository.save(newOffer);
        return OfferMapper.entityToDto(savedOffer);
    }

    public OfferResponse updateOffer(Long id, OfferRequest offerRequest, Category category, User user) {
        Offer updatedOffer = offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Offer.class.getSimpleName(), "id", id.toString()));
        if (!updatedOffer.getUser().getId().equals(user.getId())) {
            throw new SecurityException("You don't have permission to update this offer");
        }
        updatedOffer.setTitle(offerRequest.title());
        updatedOffer.setDescription(offerRequest.description());
        updatedOffer.setPrice(offerRequest.price());
        updatedOffer.setCategory(category);
        updatedOffer.setLocation(offerRequest.location());
        Offer savedOffer = offerRepository.save(updatedOffer);
        return OfferMapper.entityToDto(savedOffer);
    }

    public void deleteOffer(Long id, User user) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Offer.class.getSimpleName(), "id", id.toString()));
        if (!offer.getUser().getId().equals(user.getId())) {
            throw new SecurityException("You don't have permission to delete this offer");
        }
        offerRepository.deleteById(id);
    }

    public List<OfferResponse> getOffersByUserId(Long userId) {
        List<Offer> offers = offerRepository.findByUserId(userId);
        if (offers.isEmpty()) {
            throw new EntityNotFoundException(Offer.class.getSimpleName(), "userId", userId.toString());
        }
        return offers.stream().map(OfferMapper::entityToDto).toList();
    }
}