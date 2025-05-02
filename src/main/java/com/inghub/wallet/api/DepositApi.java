package com.inghub.wallet.api;

import com.inghub.wallet.model.TransactionRequest;
import com.inghub.wallet.model.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Deposit", description = "API for deposit operation")
public interface DepositApi {

    @PostMapping(value = "/deposit", consumes = "application/json", produces = "application/json")
    @Operation(
            summary = "Makes a Deposit",
            description = "This operation makes a Deposit.",
            tags = {"Deposit"})
    ResponseEntity<TransactionResponse> deposit(TransactionRequest request);

}
