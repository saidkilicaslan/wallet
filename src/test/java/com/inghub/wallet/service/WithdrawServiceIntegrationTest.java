package com.inghub.wallet.service;

import com.inghub.wallet.entity.*;
import com.inghub.wallet.exception.WalletIsNotAvailableForUsageException;
import com.inghub.wallet.model.TransactionRequest;
import com.inghub.wallet.repository.TransactionRepository;
import com.inghub.wallet.repository.WalletRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class WithdrawServiceIntegrationTest {

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet();
        wallet.setWalletId(UUID.randomUUID());
        wallet.setCurrency(Currency.TRY);
        wallet.setBalance(new BigDecimal("2000"));
        wallet.setUsableBalance(new BigDecimal("2000"));
        wallet.setActiveForWithdraw(true);
        wallet.setActiveForShopping(true);
        wallet = walletRepository.save(wallet);
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    void givenValidWithdrawRequest_whenWithdraw_thenWalletUpdatedAndTransactionCreated() {
        TransactionRequest request = new TransactionRequest();
        request.setWalletId(wallet.getWalletId());
        request.setAmount(new BigDecimal("500"));
        request.setSourceType(OppositePartyType.IBAN);
        Transaction transaction = withdrawService.withdraw(request);

        Wallet updatedWallet = walletRepository.findById(wallet.getId()).orElseThrow();
        assertEquals(new BigDecimal("1500"), updatedWallet.getBalance());
        assertEquals(new BigDecimal("1500"), updatedWallet.getUsableBalance());

        Transaction savedTransaction = transactionRepository.findById(transaction.getId()).orElseThrow();
        assertEquals(wallet.getId(), savedTransaction.getWallet().getId());
        assertEquals(TransactionType.WITHDRAW, savedTransaction.getType());
        assertEquals(new BigDecimal("500"), savedTransaction.getAmount());
    }

    @Test
    void givenAmountGreaterThan1000_whenWithdraw_thenTransactionStatusIsPending() {
        TransactionRequest request = new TransactionRequest();
        request.setWalletId(wallet.getWalletId());
        request.setAmount(new BigDecimal("1500"));
        request.setSourceType(OppositePartyType.IBAN);
        Transaction transaction = withdrawService.withdraw(request);
        Transaction savedTransaction = transactionRepository.findById(transaction.getId()).orElseThrow();
        assertEquals(TransactionStatus.PENDING, savedTransaction.getStatus());
    }

    @Test
    void givenInsufficientBalance_whenWithdraw_thenThrowException() {
        TransactionRequest request = new TransactionRequest();
        request.setWalletId(wallet.getWalletId());
        request.setAmount(new BigDecimal("3000")); // More than wallet balance
        request.setSourceType(OppositePartyType.IBAN);
        WalletIsNotAvailableForUsageException exception = assertThrows(
                WalletIsNotAvailableForUsageException.class,
                () -> withdrawService.withdraw(request)
        );
        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    void givenWalletInactiveForWithdraw_whenWithdrawWithIban_thenThrowException() {
        wallet.setActiveForWithdraw(false);
        walletRepository.save(wallet);

        TransactionRequest request = new TransactionRequest();
        request.setWalletId(wallet.getWalletId());
        request.setAmount(new BigDecimal("500"));
        request.setSourceType(OppositePartyType.IBAN);

        WalletIsNotAvailableForUsageException exception = assertThrows(
                WalletIsNotAvailableForUsageException.class,
                () -> withdrawService.withdraw(request)
        );
        assertEquals("Wallet is not available for withdraw", exception.getMessage());
    }

    @Test
    void givenAmountLessThanOrEqualTo1000_whenWithdraw_thenTransactionStatusIsSuccess() {
        // Given
        TransactionRequest request = new TransactionRequest();
        request.setWalletId(wallet.getWalletId());
        request.setAmount(new BigDecimal("500")); // 1000'den küçük
        request.setSourceType(OppositePartyType.IBAN);

        // When
        Transaction transaction = withdrawService.withdraw(request);

        // Then
        Transaction savedTransaction = transactionRepository.findById(transaction.getId()).orElseThrow();
        assertEquals(TransactionStatus.APPROVED, savedTransaction.getStatus());
    }
}
