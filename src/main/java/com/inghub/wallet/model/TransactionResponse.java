package com.inghub.wallet.model;

import com.inghub.wallet.entity.OppositePartyType;
import com.inghub.wallet.entity.TransactionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class TransactionResponse {
    @Schema(description = "Amount to deposit/withdraw", example = "125.89")
    private BigDecimal amount;

    @Schema(description = "Source of the deposit/withdraw. Can be an iban or payment id.", example = "TR45022158451002125458")
    private String source;

    @Schema(description = "Source type of the deposit/withdraw. Can be IBAN or PAYMENT_ID", example = "PAYMENT_ID")
    private OppositePartyType sourceType;

    @Schema(description = "Unique identifier of the wallet id", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID walletId;

    @Schema(description = "Status of the transaction. Can be one of PENDING, APPROVED, DENIED", example = "APPROVED")
    private TransactionStatus status;

    @Schema(description = "Unique identifier of the customer", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID transactionId;
}
