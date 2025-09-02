package project.murmuration.transaction;

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
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        List<TransactionResponse> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<TransactionResponse>> getTransactionByUserId(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
        Long userId = customUserDetail.getUser().getId();
        List<TransactionResponse> transactions = transactionService.getTransactionsByUserId(userId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping("/add/{offerId}")
    public ResponseEntity<TransactionResponse> processTransaction(@PathVariable Long offerId, @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        TransactionResponse response = transactionService.processTransaction(offerId, customUserDetail);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}