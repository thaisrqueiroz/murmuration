package project.murmuration.transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.murmuration.security.CustomUserDetail;
import project.murmuration.transaction.dto.TransactionResponse;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/transactions")
@RestController
@Tag(name = "Transaction", description = "Transaction management endpoints")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    @Operation(summary = "Get all transactions (Admin only)",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved list of transactions"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                @ApiResponse(responseCode = "403", description = "Forbidden access"),
                @ApiResponse(responseCode = "404", description = "Transaction not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        List<TransactionResponse> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user")
    @Operation(summary = "Get transactions for user",
            responses = {
                @ApiResponse(responseCode = "200", description = "Successfully retrieved list of user's transactions"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                @ApiResponse(responseCode = "403", description = "Forbidden access"),
                @ApiResponse(responseCode = "404", description = "Transaction not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<List<TransactionResponse>> getTransactionByUserId(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
        Long userId = customUserDetail.getUser().getId();
        List<TransactionResponse> transactions = transactionService.getTransactionsByUserId(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping("/add/{offerId}")
    @Operation(summary = "Process a transaction for an offer",
            responses = {
                @ApiResponse(responseCode = "201", description = "Transaction processed successfully"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized access"),
                @ApiResponse(responseCode = "403", description = "Forbidden access"),
                @ApiResponse(responseCode = "404", description = "Offer not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<TransactionResponse> processTransaction(@PathVariable Long offerId, @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        TransactionResponse response = transactionService.processTransaction(offerId, customUserDetail);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}