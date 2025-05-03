package com.inghub.wallet.service;

import com.inghub.wallet.entity.Wallet;
import com.inghub.wallet.exception.InvalidTransactionAmountException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class WalletDepositServiceTest {

    @Test
    void givenNullWallet_whenDeposit_thenThrowException() {
        Wallet wallet = null;
        BigDecimal amount = BigDecimal.valueOf(1000);
        assertThrows(NullPointerException.class, () -> WalletDepositService.deposit(wallet, amount));
    }

    @Test
    void givenNullWalletBalance_whenDeposit_thenBalanceAndUsableBalanceShouldIncrease() {
        Wallet wallet = new Wallet();
        BigDecimal amount = BigDecimal.valueOf(100);
        Assertions.assertNotNull(wallet);
        Assertions.assertNull(wallet.getBalance());
        Assertions.assertNull(wallet.getUsableBalance());
        Wallet updatedWallet = WalletDepositService.deposit(wallet, amount);
        Assertions.assertNotNull(updatedWallet);
        assertEquals(BigDecimal.valueOf(100), updatedWallet.getBalance());
        assertEquals(BigDecimal.valueOf(100), updatedWallet.getUsableBalance());

    }

    @Test
    void givenNegativeAmount_whenDeposit_thenThrowInvalidTransactionAmountException() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(1000));
        wallet.setUsableBalance(BigDecimal.valueOf(500));
        BigDecimal amount = BigDecimal.valueOf(-100);
        assertThrows(InvalidTransactionAmountException.class, () -> WalletDepositService.deposit(wallet, amount));
    }

    void givenNullAmount_whenDeposit_thenThrowException() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(1000));
        wallet.setUsableBalance(BigDecimal.valueOf(500));
        BigDecimal amount = null;
        assertThrows(NullPointerException.class, () -> WalletDepositService.deposit(wallet, amount));
    }

    @Test
    void givenWalletAndAmountLessThanOrEqualTo1000_whenDeposit_thenBalanceAndUsableBalanceShouldIncrease() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(1000));
        wallet.setUsableBalance(BigDecimal.valueOf(500));
        BigDecimal amount = BigDecimal.valueOf(500);
        Wallet updatedWallet = WalletDepositService.deposit(wallet, amount);

        assertEquals(BigDecimal.valueOf(1500), updatedWallet.getBalance());
        assertEquals(BigDecimal.valueOf(1000), updatedWallet.getUsableBalance());
    }

    @Test
    void givenWalletAndAmountGreaterThan1000_whenDeposit_thenOnlyBalanceShouldIncrease() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(1000));
        wallet.setUsableBalance(BigDecimal.valueOf(500));
        BigDecimal amount = BigDecimal.valueOf(1500);
        Wallet updatedWallet = WalletDepositService.deposit(wallet, amount);
        assertEquals(BigDecimal.valueOf(2500), updatedWallet.getBalance());
        assertEquals(BigDecimal.valueOf(500), updatedWallet.getUsableBalance());
    }
}
