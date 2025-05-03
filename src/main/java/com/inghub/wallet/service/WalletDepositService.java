package com.inghub.wallet.service;

import com.inghub.wallet.entity.Wallet;
import com.inghub.wallet.exception.InvalidTransactionAmountException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public final class WalletDepositService {
    private WalletDepositService() {
    }

    public static Wallet deposit(Wallet wallet, BigDecimal amount) {
        log.info("Depositing {} to wallet {}", amount, wallet.getWalletId());
        if(amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidTransactionAmountException("Deposit amount cannot be negative");
        }
        wallet.setBalance(wallet.getBalance() != null ? wallet.getBalance().add(amount): amount);
        if (amount.compareTo(BigDecimal.valueOf(1000)) <= 0) {
            wallet.setUsableBalance(wallet.getUsableBalance() != null ? wallet.getUsableBalance().add(amount): amount);
        }
        return wallet;
    }
}
