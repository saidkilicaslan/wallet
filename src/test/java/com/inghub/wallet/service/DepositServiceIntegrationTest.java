package com.inghub.wallet.service;

import com.inghub.wallet.entity.OppositePartyType;
import com.inghub.wallet.entity.Transaction;
import com.inghub.wallet.entity.TransactionStatus;
import com.inghub.wallet.entity.Wallet;
import com.inghub.wallet.exception.ApiException;
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

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class DepositServiceIntegrationTest {

    @Autowired
    private DepositService depositService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Wallet wallet;

    @BeforeEach
    void setup() {
        wallet = new Wallet();
        wallet.setWalletId(UUID.randomUUID());
        wallet.setBalance(new BigDecimal("1000"));
        wallet.setUsableBalance(new BigDecimal("1000"));
        wallet.setActiveForShopping(true);
        wallet.setActiveForWithdraw(true);
        walletRepository.save(wallet);
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    void givenAmountLessThanOrEqualTo1000_whenDeposit_thenTransactionStatusIsSuccess() throws ApiException {
        // Given
        TransactionRequest request = new TransactionRequest();
        request.setWalletId(wallet.getWalletId());
        request.setAmount(new BigDecimal("500"));
        request.setSourceType(OppositePartyType.IBAN);

        // When
        Transaction transaction = depositService.deposit(request);

        // Then
        Transaction savedTx = transactionRepository.findByTransactionId(transaction.getTransactionId()).orElseThrow();
        Wallet updatedWallet = walletRepository.findByWalletIdForUpdate(wallet.getWalletId()).orElseThrow();

        assertEquals(TransactionStatus.APPROVED, savedTx.getStatus());
        assertEquals(new BigDecimal("1500"), updatedWallet.getBalance());
        assertEquals(new BigDecimal("1500"), updatedWallet.getUsableBalance());
    }

    @Test
    void givenAmountGreaterThan1000_whenDeposit_thenTransactionStatusIsPending() throws ApiException {
        // Given
        TransactionRequest request = new TransactionRequest();
        request.setWalletId(wallet.getWalletId());
        request.setAmount(new BigDecimal("2000"));
        request.setSourceType(OppositePartyType.IBAN);

        // When
        Transaction transaction = depositService.deposit(request);

        // Then
        Transaction savedTx = transactionRepository.findById(transaction.getId()).orElseThrow();
        Wallet updatedWallet = walletRepository.findByWalletIdForUpdate(wallet.getWalletId()).orElseThrow();

        assertEquals(TransactionStatus.PENDING, savedTx.getStatus());
        assertEquals(new BigDecimal("3000"), updatedWallet.getBalance());
        assertEquals(new BigDecimal("1000"), updatedWallet.getUsableBalance()); // değişmedi çünkü 2000 > 1000
    }
}