package com.inghub.wallet.mapper;

import com.inghub.wallet.entity.Wallet;
import com.inghub.wallet.model.WalletCreate;
import com.inghub.wallet.model.WalletResponse;

import java.math.BigDecimal;

public final class WalletMapper {
    private WalletMapper(){}

    public static WalletResponse toModel(Wallet wallet) {
        WalletResponse response = new WalletResponse();
        response.setWalletId(wallet.getWalletId());
        response.setBalance(wallet.getBalance());
        response.setWalletName(wallet.getWalletName());
        response.setCurrency(wallet.getCurrency());
        response.setUsableBalance(wallet.getUsableBalance());
        response.setActiveForShopping(wallet.getActiveForShopping());
        response.setActiveForWithdraw(wallet.getActiveForWithdraw());
        return response;
    }

    public static Wallet toEntity(WalletCreate walletCreate) {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setWalletName(walletCreate.getWalletName());
        wallet.setCurrency(walletCreate.getCurrency());
        wallet.setUsableBalance(BigDecimal.ZERO);
        wallet.setActiveForShopping(walletCreate.getActiveForShopping());
        wallet.setActiveForWithdraw(walletCreate.getActiveForWithdraw());
        return wallet;
    }
}
