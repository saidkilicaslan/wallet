package com.inghub.wallet.service;

import com.inghub.wallet.entity.Transaction;
import com.inghub.wallet.entity.TransactionType;
import com.inghub.wallet.entity.Wallet;
import com.inghub.wallet.exception.ApiException;
import com.inghub.wallet.model.TransactionRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepositService {
    private final TransactionService transactionService;
    private final WalletService walletService;

    @Transactional
    public Transaction deposit(TransactionRequest transactionRequest) throws ApiException {
        Wallet wallet = WalletDepositService.deposit(walletService.findWalletForUpdate(transactionRequest.getWalletId()), transactionRequest.getAmount());
        return transactionService.createTransaction(walletService.save(wallet), transactionRequest, TransactionType.DEPOSIT);
    }
}
