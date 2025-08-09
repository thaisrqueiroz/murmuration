package project.murmuration.dtos.transaction;

import project.murmuration.dtos.user.UserMapper;
import project.murmuration.models.Transaction;

import java.util.List;

public class TransactionMapper {
    public static TransactionResponse entityToDto(Transaction transaction){
        return new TransactionResponse(transaction.getId(), transaction.getTransactionTitle(), transaction.getOffer().getId(), transaction.getOffer().getTitle(), transaction.getAmount(), UserMapper.entityToDto(transaction.getSender()), UserMapper.entityToDto(transaction.getReceiver()), transaction.getTransactionDate());
    }

    public static List<TransactionResponse> entitiesToDtos(List<Transaction> transactions){
        return transactions.stream()
                .map(TransactionMapper::entityToDto)
                .toList();
    }
}