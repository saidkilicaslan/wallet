package com.inghub.wallet.service;

import com.inghub.wallet.entity.Transaction;
import com.inghub.wallet.entity.TransactionStatus;
import com.inghub.wallet.exception.IllegalTransactionStatusException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionLifecycleService {
    private final TransactionService transactionService;
    private final WalletTransactionService walletTransactionService;

    @Transactional
    public Transaction updateStatus(UUID transactionId, TransactionStatus status) {
        Transaction transaction = transactionService.findTransaction(transactionId);
        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new IllegalTransactionStatusException("Only PENDING transactions can be updated.");
        }
        transaction.setStatus(status);
        if(status == TransactionStatus.APPROVED) {
            walletTransactionService.approve(transaction);
        } else if (status == TransactionStatus.DENIED) {
            walletTransactionService.reject(transaction);
        }
        return transactionService.save(transaction);
    }
}
