package project.murmuration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.murmuration.category.Category;
import project.murmuration.category.dto.CategoryResponse;
import project.murmuration.offer.dto.OfferRequest;
import project.murmuration.offer.dto.OfferResponse;
import project.murmuration.user.User;
import project.murmuration.user.dto.UserResponse;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;
import static project.murmuration.security.Role.USER;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {
    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private OfferService offerService;
    private Offer offer;
    private OfferResponse offerResponse;
    private OfferRequest offerRequest;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Food");

        User user = new User();
        user.setId(1L);
        user.setUsername("thais");
        user.setName("Thais");
        user.setEmail("thais@email.com");
        user.setRole(USER);

        offer = new Offer();
        offer.setId(1L);
        offer.setTitle("Pao de queijo");
        offer.setDescription("Delicious Brazilian cheese bread, twelve units");
        offer.setPrice(5);
        offer.setLocation("Valencia");
        offer.setCategory(category);
        offer.setUser(user);

        CategoryResponse categoryResponse = new CategoryResponse(1L, "Food");
        UserResponse userResponse = new UserResponse(1L, "thais", "Thais", "thais@email.com", USER);

        offerRequest = new OfferRequest("Pao de queijo", "Delicious Brazilian cheese bread, twelve units", 1L, 5, "Valencia", false);

        offerResponse = new OfferResponse(1L, "Pao de queijo", "Delicious Brazilian cheese bread, twelve units", categoryResponse, 5, userResponse, "Valencia",false);
    }

    @Test
    @DisplayName("Should return all offers")
    void getAllOffers() {
        when(offerRepository.findAll()).thenReturn(List.of(offer));

        List<OfferResponse> result = offerService.getAllOffers();

        assertThat(result).hasSize(1).containsExactly(offerResponse);
        verify(offerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return offer by id")
    void getOfferResponseById() {
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        OfferResponse result = offerService.getOfferResponseById(1L);

        assertThat(result).isEqualTo(offerResponse);
        verify(offerRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return offer by user id")
    void getOffersByUserId() {
        when(offerRepository.findByUserId(1L)).thenReturn(List.of(offer));

        List<OfferResponse> result = offerService.getOffersByUserId(1L);

        assertThat(result).hasSize(1).containsExactly(offerResponse);
        verify(offerRepository, times(1)).findByUserId(1L);
    }

    @Test
    @DisplayName("Should add a new offer")
    void addOffer() {
        Category category = offer.getCategory();
        User user = offer.getUser();

        when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        OfferResponse result = offerService.addOffer(offerRequest, category, user);

        assertThat(result).isEqualTo(offerResponse);
        verify(offerRepository, times(1)).save(any(Offer.class));
    }

    @Test
    @DisplayName("Should update an offer")
    void updateOffer() {
        Category category = offer.getCategory();
        User user = offer.getUser();

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        OfferResponse result = offerService.updateOffer(1L, offerRequest, category, user);

        assertThat(result).isEqualTo(offerResponse);
        verify(offerRepository, times(1)).findById(1L);
        verify(offerRepository, times(1)).save(any(Offer.class));
    }

    @Test
    @DisplayName("Should not update an offer if user is not the owner")
    void updateOffer_whenUserIsNotOwner() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setUsername("rubens");
        anotherUser.setName("Rubens");
        anotherUser.setEmail("rubens@email.com");
        anotherUser.setRole(USER);

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        try {
            offerService.updateOffer(1L, offerRequest, offer.getCategory(), anotherUser);
        } catch (RuntimeException exception) {
            assertThat(exception.getMessage()).isEqualTo("You don't have permission to update this offer");
        }
        verify(offerRepository, times(1)).findById(1L);
        verify(offerRepository, times(0)).save(any(Offer.class));
    }

    @Test
    @DisplayName("Should delete an offer")
    void deleteOffer() {
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));

        offerService.deleteOffer(1L, offer.getUser());

        verify(offerRepository, times(1)).findById(1L);
        verify(offerRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should not delete an offer if user is not the owner")
    void deleteOffer_whenUserIsNotOwner() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setUsername("rubens");
        anotherUser.setName("Rubens");
        anotherUser.setEmail("rubens@email.com");
        anotherUser.setRole(USER);

        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer));
        try {
            offerService.deleteOffer(1L, anotherUser);
        } catch (RuntimeException exception) {
            assertThat(exception.getMessage()).isEqualTo("You don't have permission to delete this offer");
        }

        verify(offerRepository, times(1)).findById(1L);
        verify(offerRepository, times(0)).deleteById(1L);
    }
}