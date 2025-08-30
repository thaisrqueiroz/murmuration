package project.murmuration.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("tests")
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return all transactions when user is ADMIN")
    @WithUserDetails("admin")
    void shouldReturnAllTransactions_whenUserIsAdmin_returnListOfTransactions() throws Exception {
        mockMvc.perform(get("/api/transactions").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(6)))
                .andExpect(jsonPath("$[0].offerId").value(5))
                .andExpect(jsonPath("$[0].offerTitle").value("Pet sitter"))
                .andExpect(jsonPath("$[0].userCustomer.username").value("anna_magaro"))
                .andExpect(jsonPath("$[0].userReceiver.username").value("carlos_valls"))
                .andExpect(jsonPath("$[1].offerId").value(6))
                .andExpect(jsonPath("$[1].offerTitle").value("Lend a drill"))
                .andExpect(jsonPath("$[1].userCustomer.username").value("carlos_valls"))
                .andExpect(jsonPath("$[1].userReceiver.username").value("selma_gonzalez"));
    }

    @Test
    @DisplayName("Should return transactions by user id")
    @WithUserDetails("carlos_valls")
    void getTransactionsByUserId_whenTransactionExists_returnsTransactionResponse() throws Exception {
        mockMvc.perform(get("/api/transactions/user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[1].offerId").value(6))
                .andExpect(jsonPath("$[0].offerTitle").value("Pet sitter"))
                .andExpect(jsonPath("$[0].userCustomer.username").value("anna_magaro"))
                .andExpect(jsonPath("$[0].userReceiver.username").value("carlos_valls"))
                .andExpect(jsonPath("$[1].offerId").value(6))
                .andExpect(jsonPath("$[1].offerTitle").value("Lend a drill"))
                .andExpect(jsonPath("$[1].userCustomer.username").value("carlos_valls"))
                .andExpect(jsonPath("$[1].userReceiver.username").value("selma_gonzalez"));
    }

    @Test
    @DisplayName("Should process a transaction successfully")
    @WithUserDetails("anna_magaro")
    void processTransaction_whenValidOfferAndUser_thenReturnTransactionResponse() throws Exception {
        mockMvc.perform(post("/api/transactions/add/{offerId}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.offerId").value(1))
                .andExpect(jsonPath("$.offerTitle").value("Homemade vegan cheese"))
                .andExpect(jsonPath("$.userCustomer.username").value("anna_magaro"))
                .andExpect(jsonPath("$.userReceiver.username").value("rubens_garcia"));
    }

    @Test
    @DisplayName("Should fail to process a transaction for a unique offer that has already been transacted")
    @WithUserDetails("anna_magaro")
    void processTransaction_whenUniqueOfferAlreadyTransacted_thenReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/transactions/add/{offerId}", 9).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("This unique offer has already been sold."));
    }

    @Test
    @DisplayName("Should fail to process a transaction when offer does not exist")
    @WithUserDetails("anna_magaro")
    void processTransaction_whenOfferDoesNotExist_thenReturnNotFound() throws Exception {
        mockMvc.perform(post("/api/transactions/add/{offerId}", 99).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Offer with id 99 was not found"));
    }

    @Test
    @DisplayName("Should fail to process a transaction when user does not have enough balance")
    @WithUserDetails("anna_magaro")
    void processTransaction_whenUserHasInsufficientBalance_thenReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/transactions/add/{offerId}", 4).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("You don't have enough balance to make this transaction."));
    }

    @Test
    @DisplayName("Should fail to process a transaction when user tries to buy their own offer")
    @WithUserDetails("rubens_garcia")
    void processTransaction_whenUserTriesToBuyOwnOffer_thenReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/transactions/add/{offerId}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("You cannot make a transaction to yourself."));
    }
}