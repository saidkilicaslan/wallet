package com.inghub.wallet.api;

import com.inghub.wallet.entity.Transaction;
import com.inghub.wallet.model.TransactionRequest;
import com.inghub.wallet.model.TransactionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Tag(name = "Transaction", description = "API for transaction operations")
public interface TransactionApi {
    @GetMapping(value = "/transaction/{walletId}", produces = "application/json")
    @Operation(
            summary = "List transactions of a wallet",
            description = "This operation lists transaction of a wallet.",
            tags = {"Customer"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "All data returned in one page"),
                    @ApiResponse(responseCode = "204", description = "No content"),
                    @ApiResponse(responseCode = "206", description = "Partial data returned (pagination)", headers = {
                            @Header(name = "X-Total-Count", description = "Total number of customers", schema = @Schema(type = "integer", example = "17847578")),
                            @Header(name = "X-Total-Pages", description = "Total number of pages", schema = @Schema(type = "integer", example = "15")),
                            @Header(name = "X-Current-Page", description = "Current page number", schema = @Schema(type = "integer", example = "1")),
                            @Header(name = "X-Page-Size", description = "Size of the page", schema = @Schema(type = "integer", example = "10"))
                    }),
            })
    ResponseEntity<List<TransactionResponse>> listTransactions(@Parameter(description = "Unique identifier of the wallet", required = true) UUID walletId,
                                                               @Parameter(description = "Page number. Default page number is 0") Integer page,
                                                               @Parameter(description = "Page size. Default page size is 10") Integer size,
                                                               HttpServletResponse response);


    @PostMapping(value = "/transaction/approve/{transactionId}", consumes = "application/json", produces = "application/json")
    @Operation(
            summary = "Approves a pending transaction",
            description = "This operation approves a pending transaction",
            tags = {"Transaction"})
    ResponseEntity<TransactionResponse> approve(@Parameter(description = "Unique identifier of the transaction", required = true) UUID transactionId);

    @PostMapping(value = "/transaction/deny/{transactionId}", consumes = "application/json", produces = "application/json")
    @Operation(
            summary = "Denies a pending transaction",
            description = "This operation denies a pending transaction",
            tags = {"Transaction"})
    ResponseEntity<TransactionResponse> deny(@Parameter(description = "Unique identifier of the transaction", required = true) UUID transactionId);
}
