package project.murmuration.transaction.dto;

import jakarta.validation.constraints.NotNull;

public record TransactionRequest(
        @NotNull(message = "Offer ID is required")
        Long offerId,
        Long userCustomer
) {
}