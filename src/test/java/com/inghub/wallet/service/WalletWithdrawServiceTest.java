package com.inghub.wallet.service;

import com.inghub.wallet.entity.OppositePartyType;
import com.inghub.wallet.entity.Wallet;
import com.inghub.wallet.exception.IllegalOppositePartyTypeException;
import com.inghub.wallet.exception.InvalidTransactionAmountException;
import com.inghub.wallet.exception.WalletIsNotAvailableForUsageException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WalletWithdrawServiceTest {


    @Test
    void givenNullWallet_whenWithdraw_thenThrowNullPointerException() {
        Wallet wallet = null;
        BigDecimal amount = BigDecimal.valueOf(100);
        OppositePartyType sourceType = OppositePartyType.PAYMENT_ID;
        assertThrows(NullPointerException.class, () -> WalletWithdrawService.withdraw(wallet, amount, sourceType));

    }

    @Test
    void givenNullAmount_whenWithdraw_thenThrowInvalidTransactionAmountException() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(1000));
        wallet.setUsableBalance(BigDecimal.valueOf(500));
        BigDecimal amount = null;
        OppositePartyType sourceType = OppositePartyType.PAYMENT_ID;
        assertThrows(InvalidTransactionAmountException.class, () -> WalletWithdrawService.withdraw(wallet, amount, sourceType));
    }

    @Test
    void givenNullSourceType_whenWithdraw_thenThrowIllegalOppositePartyTypeException() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(1000));
        wallet.setUsableBalance(BigDecimal.valueOf(500));
        BigDecimal amount = BigDecimal.valueOf(100);
        OppositePartyType sourceType = null;
        assertThrows(IllegalOppositePartyTypeException.class, () -> WalletWithdrawService.withdraw(wallet, amount, sourceType));
    }

    @Test
    void givenNullWalletBalance_whenWithdraw_thenThrowWalletIsNotAvailableForUsageException() {
        Wallet wallet = new Wallet();
        wallet.setBalance(null);
        wallet.setUsableBalance(BigDecimal.valueOf(500));
        BigDecimal amount = BigDecimal.valueOf(100);
        OppositePartyType sourceType = OppositePartyType.PAYMENT_ID;
        assertThrows(WalletIsNotAvailableForUsageException.class, () -> WalletWithdrawService.withdraw(wallet, amount, sourceType));
    }

    @Test
    void givenNullWalletUsableBalance_whenWithdraw_thenThrowWalletIsNotAvailableForUsageException() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(1000));
        wallet.setUsableBalance(null);
        BigDecimal amount = BigDecimal.valueOf(100);
        OppositePartyType sourceType = OppositePartyType.PAYMENT_ID;
        assertThrows(WalletIsNotAvailableForUsageException.class, () -> WalletWithdrawService.withdraw(wallet, amount, sourceType));
    }

    @Test
    void givenNegativeAmount_whenWithdraw_thenThrowInvalidTransactionAmountException() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(1000));
        wallet.setUsableBalance(BigDecimal.valueOf(500));
        BigDecimal amount = BigDecimal.valueOf(-100);
        OppositePartyType sourceType = OppositePartyType.PAYMENT_ID;
        assertThrows(InvalidTransactionAmountException.class, () -> WalletWithdrawService.withdraw(wallet, amount, sourceType));
    }

    @Test
    void givenZeroAmount_whenWithdraw_thenThrowInvalidTransactionAmountException() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(1000));
        wallet.setUsableBalance(BigDecimal.valueOf(500));
        BigDecimal amount = BigDecimal.ZERO;
        OppositePartyType sourceType = OppositePartyType.PAYMENT_ID;
        assertThrows(InvalidTransactionAmountException.class, () -> WalletWithdrawService.withdraw(wallet, amount, sourceType));
    }

    @Test
    void givenInsufficientBalance_whenWithdraw_thenThrowWalletIsNotAvailableForUsageException() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(100));
        wallet.setUsableBalance(BigDecimal.valueOf(50));
        BigDecimal amount = BigDecimal.valueOf(200);
        OppositePartyType sourceType = OppositePartyType.PAYMENT_ID;
        assertThrows(WalletIsNotAvailableForUsageException.class, () -> WalletWithdrawService.withdraw(wallet, amount, sourceType));
    }

    @Test
    void givenInactiveShoppingWalletAndPaymentId_whenWithdraw_thenThrowWalletIsNotAvailableForUsageException() {
        Wallet wallet = new Wallet();
        wallet.setActiveForShopping(false);
        wallet.setBalance(BigDecimal.valueOf(1000));
        wallet.setUsableBalance(BigDecimal.valueOf(500));
        BigDecimal amount = BigDecimal.valueOf(100);
        OppositePartyType sourceType = OppositePartyType.PAYMENT_ID;
        assertThrows(WalletIsNotAvailableForUsageException.class, () -> WalletWithdrawService.withdraw(wallet, amount, sourceType));
    }

    @Test
    void givenInactiveWithdrawWalletAndIban_whenWithdraw_thenThrowWalletIsNotAvailableForUsageException() {
        Wallet wallet = new Wallet();
        wallet.setActiveForWithdraw(false);
        wallet.setActiveForShopping(true);
        wallet.setBalance(BigDecimal.valueOf(1000));
        wallet.setUsableBalance(BigDecimal.valueOf(500));
        BigDecimal amount = BigDecimal.valueOf(100);
        OppositePartyType sourceType = OppositePartyType.IBAN;
        assertThrows(WalletIsNotAvailableForUsageException.class, () -> WalletWithdrawService.withdraw(wallet, amount, sourceType));
    }


    @Test
    void givenSufficientBalanceAndActiveWallet_whenWithdrawWithPaymentId_thenBalanceAndUsableBalanceShouldDecrease() {
        Wallet wallet = new Wallet();
        wallet.setActiveForShopping(true);
        wallet.setActiveForWithdraw(false);
        wallet.setBalance(BigDecimal.valueOf(1000));
        wallet.setUsableBalance(BigDecimal.valueOf(500));
        BigDecimal amount = BigDecimal.valueOf(100);
        OppositePartyType sourceType = OppositePartyType.PAYMENT_ID;

        WalletWithdrawService.withdraw(wallet, amount, sourceType);

        assertEquals(BigDecimal.valueOf(900), wallet.getBalance());
        assertEquals(BigDecimal.valueOf(400), wallet.getUsableBalance());
    }

    @Test
    void givenSufficientBalanceAndActiveWallet_whenWithdrawWithIban_thenBalanceAndUsableBalanceShouldDecrease() {
        Wallet wallet = new Wallet();
        wallet.setActiveForWithdraw(true);
        wallet.setActiveForShopping(false);
        wallet.setBalance(BigDecimal.valueOf(1000));
        wallet.setUsableBalance(BigDecimal.valueOf(500));
        BigDecimal amount = BigDecimal.valueOf(100);
        OppositePartyType sourceType = OppositePartyType.IBAN;

        WalletWithdrawService.withdraw(wallet, amount, sourceType);

        assertEquals(BigDecimal.valueOf(900), wallet.getBalance());
        assertEquals(BigDecimal.valueOf(400), wallet.getUsableBalance());
    }

    @Test
    void givenSufficientBalanceAndAmountAboveLimit_whenWithdraw_thenOnlyBalanceShouldDecrease() {
        Wallet wallet = new Wallet();
        wallet.setActiveForShopping(true);
        wallet.setActiveForWithdraw(true);
        wallet.setBalance(BigDecimal.valueOf(2000));
        wallet.setUsableBalance(BigDecimal.valueOf(2000));
        BigDecimal amount = BigDecimal.valueOf(1500);
        OppositePartyType sourceType = OppositePartyType.PAYMENT_ID;

        WalletWithdrawService.withdraw(wallet, amount, sourceType);

        assertEquals(BigDecimal.valueOf(500), wallet.getBalance());
        assertEquals(BigDecimal.valueOf(2000), wallet.getUsableBalance());
    }


    @Test
    void givenAmountEqualToThousand_whenWithdraw_thenUsableBalanceShouldDecrease() {
        Wallet wallet = new Wallet();
        wallet.setActiveForShopping(true);
        wallet.setActiveForWithdraw(false);
        wallet.setBalance(BigDecimal.valueOf(2000));
        wallet.setUsableBalance(BigDecimal.valueOf(2000));
        BigDecimal amount = BigDecimal.valueOf(1000);
        OppositePartyType sourceType = OppositePartyType.PAYMENT_ID;

        WalletWithdrawService.withdraw(wallet, amount, sourceType);

        assertEquals(BigDecimal.valueOf(1000), wallet.getBalance());
        assertEquals(BigDecimal.valueOf(1000), wallet.getUsableBalance());
    }

    @Test
    void givenAmountGreaterThanThousand_whenWithdraw_thenUsableBalanceShouldNotChange() {
        Wallet wallet = new Wallet();
        wallet.setActiveForWithdraw(false);
        wallet.setActiveForShopping(true);
        wallet.setBalance(BigDecimal.valueOf(2000));
        wallet.setUsableBalance(BigDecimal.valueOf(2000));
        BigDecimal amount = BigDecimal.valueOf(1500);
        OppositePartyType sourceType = OppositePartyType.PAYMENT_ID;

        WalletWithdrawService.withdraw(wallet, amount, sourceType);

        assertEquals(BigDecimal.valueOf(500), wallet.getBalance());
        assertEquals(BigDecimal.valueOf(2000), wallet.getUsableBalance());
    }

}
