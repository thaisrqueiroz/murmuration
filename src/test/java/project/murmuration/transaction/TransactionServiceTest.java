package project.murmuration.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.murmuration.category.Category;
import project.murmuration.exceptions.EntityNotFoundException;
import project.murmuration.offer.Offer;
import project.murmuration.offer.OfferRepository;
import project.murmuration.security.CustomUserDetail;
import project.murmuration.transaction.dto.TransactionResponse;
import project.murmuration.user.User;
import project.murmuration.user.UserRepository;
import project.murmuration.user.dto.UserResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static project.murmuration.security.Role.USER;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionService transactionService;
    private TransactionResponse transactionResponse;
    private Transaction transaction;
    private User userCustomer;
    private User userReceiver;
    private CustomUserDetail customUserDetail;
    private Offer offerUniqueFalse;

    @BeforeEach
    void setUp() {
        Category categoryFood = new Category();
        categoryFood.setId(1L);
        categoryFood.setName("Food");

        userCustomer = new User();
        userCustomer.setId(1L);
        userCustomer.setUsername("thais");
        userCustomer.setName("Thais");
        userCustomer.setEmail("thais@email.com");
        userCustomer.setBalance(100);
        userCustomer.setRole(USER);

        userReceiver = new User();
        userReceiver.setId(2L);
        userReceiver.setUsername("rubens");
        userReceiver.setName("Rubens");
        userReceiver.setEmail("rubens@email.com");
        userReceiver.setRole(USER);

        offerUniqueFalse = new Offer();
        offerUniqueFalse.setId(1L);
        offerUniqueFalse.setTitle("Pao de queijo");
        offerUniqueFalse.setDescription("Delicious Brazilian cheese bread, twelve units");
        offerUniqueFalse.setPrice(5);
        offerUniqueFalse.setLocation("Valencia");
        offerUniqueFalse.setCategory(categoryFood);
        offerUniqueFalse.setUser(userReceiver);
        offerUniqueFalse.setUnique(false);

        customUserDetail = new CustomUserDetail(userCustomer);

        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setOffer(offerUniqueFalse);
        transaction.setTransactionDate(LocalDateTime.of(2025, 8, 27, 10, 30, 0));
        transaction.setUserCustomer(userCustomer);
        transaction.setUserReceiver(userReceiver);

        UserResponse customerResponse = new UserResponse(1L, "thais", "Thais", "thais@email.com", USER);
        UserResponse receiverResponse = new UserResponse(2L, "rubens", "Rubens", "rubens@email.com", USER);

        transactionResponse = new TransactionResponse(
                transaction.getId(),
                transaction.getOffer().getId(),
                transaction.getOffer().getTitle(),
                customerResponse,
                receiverResponse,
                transaction.getTransactionDate());
    }

    @Test
    @DisplayName("Should return all transactions")
    void getAllTransactions_whenTransactionsExist_returnsListOfTransactions() {
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        List<TransactionResponse> result = transactionService.getAllTransactions();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).id()).isEqualTo(1L);
        assertThat(result.get(0).offerId()).isEqualTo(1L);
        assertThat(result.get(0).offerTitle()).isEqualTo("Pao de queijo");

        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return transactions by user id")
    void getTransactionsByUserId_whenTransactionExists_returnsTransactionResponse() {
        when(transactionRepository.findByUserCustomerIdOrUserReceiverId(1L, 1L)).thenReturn(List.of(transaction));

        List<TransactionResponse> result = transactionService.getTransactionsByUserId(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).userCustomer().id()).isEqualTo(1L);
        verify(transactionRepository, times(1)).findByUserCustomerIdOrUserReceiverId(1L, 1L);
    }

    @Test
    @DisplayName("Should throw exception when no transactions found for user id")
    void getTransactionsByUserId_whenNoTransactionExists_throwsException() {
        when(transactionRepository.findByUserCustomerIdOrUserReceiverId(3L, 3L)).thenReturn(List.of());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
            transactionService.getTransactionsByUserId(3L));

        assertEquals("Transaction with userId 3 was not found", exception.getMessage());
        verify(transactionRepository, times(1)).findByUserCustomerIdOrUserReceiverId(3L, 3L);
    }

    @Test
    @DisplayName("Should process a transaction successfully")
    void processTransaction_whenValidOfferAndUser_thenReturnTransactionResponse() {
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offerUniqueFalse));
        when(userRepository.findById(1L)).thenReturn((Optional.of(userCustomer)));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionResponse result = transactionService.processTransaction(1L, customUserDetail);

        assertThat(result.offerId()).isEqualTo(1L);
        assertThat(result.userCustomer().id()).isEqualTo(1L);
        assertThat(result.userReceiver().id()).isEqualTo(2L);

        verify(userRepository, times(2)).save(any(User.class));
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }
}