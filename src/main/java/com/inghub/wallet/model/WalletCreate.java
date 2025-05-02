package com.inghub.wallet.model;

import com.inghub.wallet.entity.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletCreate {
    @NotBlank
    private String walletName;

    @NotNull
    private Currency currency;

    @NotNull
    private Boolean activeForShopping;

    @NotNull
    private Boolean activeForWithdraw;


}
