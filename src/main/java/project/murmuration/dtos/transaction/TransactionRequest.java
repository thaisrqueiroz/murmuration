package project.murmuration.dtos.transaction;

import jakarta.validation.constraints.NotNull;

public record TransactionRequest(
        @NotNull(message = "Offer ID is required")
        Long offerId
) {
}