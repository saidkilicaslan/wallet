package com.inghub.wallet.api;

import com.inghub.wallet.model.TransactionRequest;
import com.inghub.wallet.model.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Withdraw", description = "API for withdraw operations")
public interface WithdrawApi {

    @PostMapping(value = "/withdraw", consumes = "application/json", produces = "application/json")
    @Operation(
            summary = "Makes a Withdrawal",
            description = "This operation makes a Withdrawal.",
            tags = {"Withdraw"})
    ResponseEntity<TransactionResponse> withdraw(TransactionRequest request);
}
