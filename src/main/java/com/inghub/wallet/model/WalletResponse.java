package com.inghub.wallet.model;

import com.inghub.wallet.entity.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class WalletResponse {
    private String walletName;
    private UUID walletId;
    private Currency currency;
    private Boolean activeForShopping;
    private Boolean activeForWithdraw;
    private BigDecimal balance;
    private BigDecimal usableBalance;
}
