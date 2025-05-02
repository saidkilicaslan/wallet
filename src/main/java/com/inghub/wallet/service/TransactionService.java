package com.inghub.wallet.service;

import com.inghub.wallet.entity.Transaction;
import com.inghub.wallet.entity.TransactionStatus;
import com.inghub.wallet.entity.TransactionType;
import com.inghub.wallet.entity.Wallet;
import com.inghub.wallet.exception.ApiException;
import com.inghub.wallet.exception.ResultNotFoundException;
import com.inghub.wallet.model.TransactionRequest;
import com.inghub.wallet.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Page<Transaction> listTransactions(UUID walletId, Integer page, Integer size) {
        return transactionRepository.findAllByWallet_walletId(walletId, Pageable.ofSize(size).withPage(page));
    }

    public Transaction findTransaction(UUID transactionId) throws ApiException {
        return transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResultNotFoundException("Transaction not found"));
    }

    public Transaction createTransaction(Wallet wallet, TransactionRequest transactionRequest, TransactionType transactionType){
        Transaction transaction = new Transaction();
        transaction.setType(transactionType);
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setOppositePartyType(transactionRequest.getSourceType());
        transaction.setOppositeParty(transactionRequest.getSource());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setWallet(wallet);
        if (transactionRequest.getAmount().compareTo(BigDecimal.valueOf(1000)) > 0) {
            transaction.setStatus(TransactionStatus.PENDING);
        } else {
            transaction.setStatus(TransactionStatus.APPROVED);
        }
        return transactionRepository.save(transaction);
    }

}
