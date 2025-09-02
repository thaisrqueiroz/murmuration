package project.murmuration.offer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Offer", description = "Offer management endpoints")
public class OfferController {
    private final OfferService offerService;
    private final CategoryService categoryService;

    @GetMapping
    @Operation(summary = "Get all offers",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved list of offers"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "404", description = "Offer not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<List<OfferResponse>> getAllOffers() {
        List<OfferResponse> offers = offerService.getAllOffers();
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get offer by ID",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved the offer"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "404", description = "Offer not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<OfferResponse> getOfferById(@PathVariable Long id) {
        OfferResponse offerResponse = offerService.getOfferResponseById(id);
        return new ResponseEntity<>(offerResponse, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get offers by User ID",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved list of offers for the user"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "404", description = "User or offers not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<List<OfferResponse>> getOffersByUserId(@PathVariable Long userId) {
        List<OfferResponse> offers = offerService.getOffersByUserId(userId);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add a new offer",
            responses = {
                @ApiResponse(responseCode = "201", description = "Offer created successfully"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                @ApiResponse(responseCode = "403", description = "Forbidden access"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<OfferResponse> addOffer(@RequestBody OfferRequest offerRequest, @AuthenticationPrincipal CustomUserDetail userDetail) {
        Category category = categoryService.getCategoryEntityById(offerRequest.categoryId());
        OfferResponse createOffer = offerService.addOffer(offerRequest, category, userDetail.getUser());
        return new ResponseEntity<>(createOffer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing offer",
            responses = {
                @ApiResponse(responseCode = "200", description = "Offer updated successfully"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                @ApiResponse(responseCode = "403", description = "Forbidden access"),
                @ApiResponse(responseCode = "404", description = "Offer not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<OfferResponse> updateOffer(@PathVariable Long id, @RequestBody OfferRequest offerRequest, @AuthenticationPrincipal CustomUserDetail userDetail) {
        Category category = categoryService.getCategoryEntityById(offerRequest.categoryId());
        OfferResponse updatedOffer = offerService.updateOffer(id, offerRequest, category, userDetail.getUser());
        return ResponseEntity.ok(updatedOffer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an offer",
            responses = {
                @ApiResponse(responseCode = "204", description = "Offer deleted successfully"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                @ApiResponse(responseCode = "403", description = "Forbidden access"),
                @ApiResponse(responseCode = "404", description = "Offer not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<Object> deleteOffer(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetail userDetail) {
        offerService.deleteOffer(id, userDetail.getUser());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}