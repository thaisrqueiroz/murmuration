package project.murmuration.transaction.dto;

import project.murmuration.user.dto.UserResponse;

import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        String transactionTitle,
        Long offerId,
        String offerTitle,
        int amount,
        UserResponse sender,
        UserResponse receiver,
        LocalDateTime transactionDate
) {
}
