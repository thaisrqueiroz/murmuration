package project.murmuration.transaction.dto;

import project.murmuration.user.dto.UserResponse;

import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        Long offerId,
        String offerTitle,
        UserResponse userCustomer,
        UserResponse userReceiver,
        LocalDateTime transactionDate
) {
}