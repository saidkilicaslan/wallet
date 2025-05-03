package com.inghub.wallet.service;

import com.inghub.wallet.entity.OppositePartyType;
import com.inghub.wallet.entity.Wallet;
import com.inghub.wallet.exception.IllegalOppositePartyTypeException;
import com.inghub.wallet.exception.InvalidTransactionAmountException;
import com.inghub.wallet.exception.WalletIsNotAvailableForUsageException;

import java.math.BigDecimal;

public final class WalletWithdrawService {
    private WalletWithdrawService() {
    }

    public static Wallet withdraw(Wallet wallet, BigDecimal amount, OppositePartyType sourceType) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionAmountException("Amount cannot be null");
        }
        if (sourceType == null) {
            throw new IllegalOppositePartyTypeException("Source type cannot be null");
        }
        if (wallet.getUsableBalance() == null || wallet.getBalance() == null || wallet.getUsableBalance().compareTo(amount) < 0 || wallet.getBalance().compareTo(amount) < 0) {
            throw new WalletIsNotAvailableForUsageException("Insufficient balance");
        }
        if (Boolean.FALSE.equals(wallet.getActiveForShopping()) && OppositePartyType.PAYMENT_ID.equals(sourceType)) {
            throw new WalletIsNotAvailableForUsageException("Wallet is not available for shopping");
        }
        if (Boolean.FALSE.equals(wallet.getActiveForWithdraw()) && OppositePartyType.IBAN.equals(sourceType)) {
            throw new WalletIsNotAvailableForUsageException("Wallet is not available for withdraw");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
        if (amount.compareTo(BigDecimal.valueOf(1000)) <= 0) {
            wallet.setUsableBalance(wallet.getUsableBalance().subtract(amount));
        }
        return wallet;
    }
}
