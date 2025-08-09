package project.murmuration.dtos.transaction;

import project.murmuration.dtos.user.UserResponse;

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
