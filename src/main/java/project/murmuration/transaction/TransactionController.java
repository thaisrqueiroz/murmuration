package project.murmuration.transaction;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.murmuration.security.CustomUserDetail;
import project.murmuration.transaction.dto.TransactionRequest;
import project.murmuration.transaction.dto.TransactionResponse;

@AllArgsConstructor
@RequestMapping("/api/transactions")
@RestController
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> processTransaction(@RequestBody TransactionRequest transactionRequest, @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        TransactionResponse response = transactionService.processTransaction(transactionRequest, customUserDetail);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}