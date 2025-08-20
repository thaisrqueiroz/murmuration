package project.murmuration.offer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.murmuration.category.Category;
import project.murmuration.exceptions.EntityNotFoundException;
import project.murmuration.offer.dto.OfferMapper;
import project.murmuration.offer.dto.OfferRequest;
import project.murmuration.offer.dto.OfferResponse;

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

    public OfferResponse addOffer(Offer offer) {
        Offer savedOffer = offerRepository.save(offer);
        return OfferMapper.entityToDto(savedOffer);
    }

    public OfferResponse updateOffer(Long id, OfferRequest offerRequest, Category category) {
        Offer updatedOffer = offerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Offer.class.getSimpleName(), "id", id.toString()));
        updatedOffer.setTitle(offerRequest.title());
        updatedOffer.setDescription(offerRequest.description());
        updatedOffer.setPrice(offerRequest.price());
        updatedOffer.setCategory(category);
        Offer savedOffer = offerRepository.save(updatedOffer);
        return OfferMapper.entityToDto(savedOffer);
    }

    public void deleteOffer(Long id) {
        if (!offerRepository.existsById(id)) {
            throw new EntityNotFoundException(Offer.class.getSimpleName(), "id", id.toString());
        }
        getOfferById(id);
        offerRepository.deleteById(id);
    }
}