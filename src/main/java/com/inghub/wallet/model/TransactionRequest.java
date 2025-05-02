package com.inghub.wallet.model;

import com.inghub.wallet.entity.OppositePartyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class TransactionRequest {
    @Schema(description = "Amount to deposit/withdraw", example = "125.89" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal amount;

    @NotBlank(message = "source is mandatory")
    @Schema(description = "Source of the deposit/withdraw. Can be an iban or payment id.", example = "TR45022158451002125458" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private String source;

    @NotNull
    @Schema(description = "Source type of the deposit/withdraw. Can be IBAN or PAYMENT_ID", example = "PAYMENT_ID" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private OppositePartyType sourceType;

    @NotNull
    @Schema(description = "Wallet id of the customer", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6" ,requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID walletId;
}
