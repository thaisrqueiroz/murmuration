package project.murmuration.offer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import project.murmuration.offer.dto.OfferRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class OfferControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return all offers")
    void shouldReturnAllOffers() throws Exception {
        mockMvc.perform(get("/api/offers").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(9)))
                .andExpect(jsonPath("$[0].title").value("Homemade vegan cheese"))
                .andExpect(jsonPath("$[0].description").value("Fermented almond cheese with herbs"))
                .andExpect(jsonPath("$[0].price").value(10))
                .andExpect(jsonPath("$[0].location").value("Valencia"))
                .andExpect(jsonPath("$[0].isUnique").value(false))
                .andExpect(jsonPath("$[1].title").value("Yoga class in the park"))
                .andExpect(jsonPath("$[1].description").value("1-hour Hatha Yoga session"))
                .andExpect(jsonPath("$[1].price").value(15))
                .andExpect(jsonPath("$[1].location").value("Paterna"))
                .andExpect(jsonPath("$[0].isUnique").value(false));
    }

    @Test
    @DisplayName("Should return offer by ID")
    void getOfferById_whenOfferExists_returnsOfferResponse() throws Exception {
        mockMvc.perform(get("/api/offers/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Homemade vegan cheese"))
                .andExpect(jsonPath("$.description").value("Fermented almond cheese with herbs"))
                .andExpect(jsonPath("$.price").value(10))
                .andExpect(jsonPath("$.location").value("Valencia"))
                .andExpect(jsonPath("$.isUnique").value(false));
    }

    @Test
    @DisplayName("Should return offer by user id")
    void getOfferByUserId_whenUserHasOffers_returnsListOfOffer() throws Exception {
        mockMvc.perform(get("/api/offers/user/{userId}", 2).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Homemade vegan cheese"))
                .andExpect(jsonPath("$[0].description").value("Fermented almond cheese with herbs"))
                .andExpect(jsonPath("$[0].price").value(10))
                .andExpect(jsonPath("$[0].location").value("Valencia"))
                .andExpect(jsonPath("$[0].isUnique").value(false));
    }

    @Test
    @DisplayName("Should create a new offer")
    @WithUserDetails("rubens_garcia")
    void addOffer_whenUserIsAuthenticated_returnsOfferResponse() throws Exception {
        OfferRequest newOffer = new OfferRequest("Guitar lessons", "Learn to play acoustic guitar", 4L, 10, "Paterna", false);

        mockMvc.perform(post("/api/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOffer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Guitar lessons"))
                .andExpect(jsonPath("$.description").value("Learn to play acoustic guitar"))
                .andExpect(jsonPath("$.price").value(10))
                .andExpect(jsonPath("$.location").value("Paterna"))
                .andExpect(jsonPath("$.isUnique").value(false));
    }

    @Test
    @DisplayName("Should update an existing offer")
    @WithUserDetails("rubens_garcia")
    void updateOffer_whenOfferExists_returnsOfferResponse() throws Exception {
        OfferRequest updatedOffer = new OfferRequest("Homemade vegan cheese", "Fermented cashed cheese, 300 grams", 1L, 12, "Paterna", false);

        mockMvc.perform(put("/api/offers/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOffer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Homemade vegan cheese"))
                .andExpect(jsonPath("$.description").value("Fermented cashed cheese, 300 grams"))
                .andExpect(jsonPath("$.price").value(12))
                .andExpect(jsonPath("$.location").value("Paterna"))
                .andExpect(jsonPath("$.isUnique").value(false));
    }

    @Test
    @DisplayName("Should delete an offer")
    @WithUserDetails("rubens_garcia")
    void deleteOffer_whenOfferExists_returnsVoid() throws Exception {
        mockMvc.perform(delete("/api/offers/{id}", 1))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/api/offers/{id}", 1))
                .andExpect(status().isNotFound());
    }
}