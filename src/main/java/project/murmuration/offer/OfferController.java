package project.murmuration.offer;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.murmuration.category.Category;
import project.murmuration.category.CategoryService;
import project.murmuration.offer.dto.OfferRequest;
import project.murmuration.offer.dto.OfferResponse;
import project.murmuration.security.CustomUserDetail;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/offers")
@RestController
public class OfferController {
    private final OfferService offerService;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<OfferResponse>> getAllOffers() {
        List<OfferResponse> offers = offerService.getAllOffers();
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferResponse> getOfferById(@PathVariable Long id) {
        OfferResponse offerResponse = offerService.getOfferResponseById(id);
        return new ResponseEntity<>(offerResponse, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OfferResponse>> getOffersByUserId(@PathVariable Long userId) {
        List<OfferResponse> offers = offerService.getOffersByUserId(userId);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OfferResponse> addOffer(@RequestBody OfferRequest offerRequest, @AuthenticationPrincipal CustomUserDetail userDetail) {
        Category category = categoryService.getCategoryEntityById(offerRequest.categoryId());
        OfferResponse createOffer = offerService.addOffer(offerRequest, category, userDetail.getUser());
        return new ResponseEntity<>(createOffer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfferResponse> updateOffer(@PathVariable Long id, @RequestBody OfferRequest offerRequest, @AuthenticationPrincipal CustomUserDetail userDetail) {
        Category category = categoryService.getCategoryEntityById(offerRequest.categoryId());
        OfferResponse updatedOffer = offerService.updateOffer(id, offerRequest, category, userDetail.getUser());
        return ResponseEntity.ok(updatedOffer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOffer(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetail userDetail) {
        offerService.deleteOffer(id, userDetail.getUser());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}