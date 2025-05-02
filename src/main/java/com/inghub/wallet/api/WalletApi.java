package com.inghub.wallet.api;

import com.inghub.wallet.entity.Currency;
import com.inghub.wallet.exception.ApiException;
import com.inghub.wallet.model.WalletCreate;
import com.inghub.wallet.model.WalletResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Wallet", description = "API for wallet operations")
public interface WalletApi {

    @PostMapping(value = "/wallet", consumes = "application/json", produces = "application/json")
    @Operation(
            summary = "Creates a Wallet",
            description = "This operation creates a Wallet entity.",
            tags = {"Wallet"})
    ResponseEntity<WalletResponse> createWallet(WalletCreate walletCreateRequest);

    @GetMapping(value = "/wallet/{tckn}", produces = "application/json")
    ResponseEntity<List<WalletResponse>> listWallets(@Parameter(description = "tckn of the customer", required = true) String tckn,
                                                    @Parameter(description = "Currency") Currency currency,
                                                    @Parameter(description = "Exact amount") BigDecimal exactAmount,
                                                    @Parameter(description = "Minimum amount") BigDecimal minAmount,
                                                    @Parameter(description = "Maximum amount") BigDecimal maxAmount,
                                                    @Parameter(description = "Page number. Default page number is 0") Integer page,
                                                    @Parameter(description = "Page size. Default page size is 10") Integer size, HttpServletResponse response) throws ApiException;
}
