package com.inghub.wallet.service;

import com.inghub.wallet.entity.Transaction;
import com.inghub.wallet.entity.TransactionType;
import com.inghub.wallet.entity.Wallet;
import com.inghub.wallet.exception.ApiException;
import com.inghub.wallet.exception.WalletIsNotAvailableForUsageException;
import com.inghub.wallet.model.TransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DepositServiceTest {

    @Mock
    private WalletService walletService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private DepositService depositService;

    private Wallet wallet;
    private TransactionRequest transactionRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        wallet = new Wallet();
        wallet.setWalletId(UUID.randomUUID());
        wallet.setBalance(BigDecimal.valueOf(2000));
        wallet.setUsableBalance(BigDecimal.valueOf(1000));
        wallet.setActiveForShopping(true);
        wallet.setActiveForWithdraw(true);

        transactionRequest = new TransactionRequest();
        transactionRequest.setWalletId(wallet.getWalletId());
        transactionRequest.setAmount(BigDecimal.valueOf(500));
    }

    @Test
    public void givenValidTransactionRequest_whenDeposit_thenWalletBalanceShouldBeUpdated() throws ApiException {
        when(walletService.findWalletForUpdate(wallet.getWalletId())).thenReturn(wallet);
        when(transactionService.createTransaction(any(), any(), eq(TransactionType.DEPOSIT))).thenReturn(new Transaction());
        Transaction transaction = depositService.deposit(transactionRequest);
        assertNotNull(transaction);
        assertEquals(BigDecimal.valueOf(2000), wallet.getBalance());
        assertEquals(BigDecimal.valueOf(1500), wallet.getUsableBalance());
        verify(walletService, times(1)).save(wallet);
        verify(transactionService, times(1)).createTransaction(any(), any(), eq(TransactionType.DEPOSIT));
    }

    @Test
    public void givenInsufficientBalance_whenDeposit_thenThrowWalletIsNotAvailableForUsageException() {
        // Arrange
        transactionRequest.setAmount(BigDecimal.valueOf(3000));  // Amount exceeds balance

        when(walletService.findWalletForUpdate(wallet.getWalletId())).thenReturn(wallet);

        // Act & Assert
        WalletIsNotAvailableForUsageException exception = assertThrows(WalletIsNotAvailableForUsageException.class, () -> {
            depositService.deposit(transactionRequest);
        });
        assertEquals("Insufficient balance", exception.getMessage());
        verify(walletService, times(0)).save(wallet);  // Ensure save is not called
    }

    @Test
    public void givenInactiveWalletForWithdraw_whenDeposit_thenThrowWalletIsNotAvailableForUsageException() {
        wallet.setActiveForWithdraw(false);  // Wallet is not active for withdraw
        when(walletService.findWalletForUpdate(wallet.getWalletId())).thenReturn(wallet);

        // Act & Assert
        WalletIsNotAvailableForUsageException exception = assertThrows(WalletIsNotAvailableForUsageException.class, () -> {
            depositService.deposit(transactionRequest);
        });
        assertEquals("Wallet is not available for withdraw", exception.getMessage());
        verify(walletService, times(0)).save(wallet);
    }
}
