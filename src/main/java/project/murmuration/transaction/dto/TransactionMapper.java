package project.murmuration.transaction.dto;

import project.murmuration.user.dto.UserMapper;
import project.murmuration.transaction.Transaction;

import java.util.List;

public class TransactionMapper {
    public static TransactionResponse entityToDto(Transaction transaction){
        return new TransactionResponse(transaction.getId(), transaction.getOffer().getId(), transaction.getOffer().getTitle(), UserMapper.entityToDto(transaction.getUserCustomer()), UserMapper.entityToDto(transaction.getUserReceiver()), transaction.getTransactionDate());
    }

    public static List<TransactionResponse> entityToDto(List<Transaction> transactions){
        return transactions.stream()
                .map(TransactionMapper::entityToDto)
                .toList();
    }
}