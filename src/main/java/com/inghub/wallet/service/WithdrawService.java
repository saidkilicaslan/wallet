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
public class WithdrawService {
    private final WalletService walletService;
    private final TransactionService transactionService;

    @Transactional
    public Transaction withdraw(TransactionRequest transactionRequest) throws ApiException {
        Wallet wallet = WalletWithdrawService.withdraw(walletService.findWalletForUpdate(transactionRequest.getWalletId()), transactionRequest.getAmount(), transactionRequest.getSourceType());
        return  transactionService.createTransaction(walletService.save(wallet), transactionRequest, TransactionType.WITHDRAW);
    }
}
