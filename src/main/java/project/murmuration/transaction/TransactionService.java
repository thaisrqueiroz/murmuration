package project.murmuration.transaction;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.murmuration.exceptions.EntityNotFoundException;
import project.murmuration.exceptions.InsufficientBalanceException;
import project.murmuration.offer.Offer;
import project.murmuration.offer.OfferRepository;
import project.murmuration.security.CustomUserDetail;
import project.murmuration.transaction.dto.TransactionMapper;
import project.murmuration.transaction.dto.TransactionRequest;
import project.murmuration.transaction.dto.TransactionResponse;
import project.murmuration.user.User;
import project.murmuration.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class TransactionService {
    public final TransactionRepository transactionRepository;
    public final UserRepository userRepository;
    public final OfferRepository offerRepository;

    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return TransactionMapper.entityToDto(transactions);
    }

    public List<TransactionResponse> getTransactionsByUserId(Long userId) {
        List<Transaction> transactions = transactionRepository.findByUserCustomerIdOrUserReceiverId(userId, userId);
        if (transactions.isEmpty()){
            throw new EntityNotFoundException(Transaction.class.getSimpleName(), "userId", userId.toString());
        }
        return TransactionMapper.entityToDto(transactions);
    }

    public TransactionResponse processTransaction(TransactionRequest request, CustomUserDetail customUserDetail) {
        Offer offer = offerRepository.findById(request.offerId()).orElseThrow(() -> new EntityNotFoundException(Offer.class.getSimpleName(), "id", request.offerId().toString()));
        User userCustomer = userRepository.findById(customUserDetail.getUser().getId()).orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName(), "id", customUserDetail.getUser().getId().toString()));
        User userReceiver = offer.getUser();

        if(!customUserDetail.getUser().getId().equals(userCustomer.getId())) {
            throw new SecurityException("You don't have permission to make this transaction");
        }

        if(userCustomer.getBalance() < offer.getPrice()) {
            throw new InsufficientBalanceException("You don't have enough balance to make this transaction");
        }

        if(userCustomer.getId().equals(userReceiver.getId())) {
            throw new IllegalArgumentException("You cannot make a transaction to yourself");
        }

        userCustomer.setBalance(userCustomer.getBalance() - offer.getPrice());
        userReceiver.setBalance(userReceiver.getBalance() + offer.getPrice());

        userRepository.save(userCustomer);
        userRepository.save(userReceiver);

        Transaction transaction = new Transaction();
        transaction.setOffer(offer);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setUserCustomer(userCustomer);
        transaction.setUserReceiver(userReceiver);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.entityToDto(savedTransaction);
    }
}