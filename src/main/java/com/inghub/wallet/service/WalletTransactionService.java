package com.inghub.wallet.service;

import com.inghub.wallet.entity.Transaction;
import com.inghub.wallet.entity.TransactionType;
import com.inghub.wallet.entity.Wallet;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WalletTransactionService {
    private final WalletService walletService;

    @Transactional
    public Wallet reject(Transaction transaction) {
        Wallet wallet = walletService.findWalletForUpdate(transaction.getWallet().getWalletId());
        if(transaction.getType() == TransactionType.DEPOSIT) {
            wallet.setUsableBalance(wallet.getUsableBalance().add(transaction.getAmount()));
        } else if (transaction.getType() == TransactionType.WITHDRAW) {
            wallet.setUsableBalance(wallet.getUsableBalance().subtract(transaction.getAmount()));
        }
        return walletService.save(wallet);
    }

    @Transactional
    public Wallet approve(Transaction transaction) {
        Wallet wallet = walletService.findWalletForUpdate(transaction.getWallet().getWalletId());
        if(transaction.getType() == TransactionType.DEPOSIT) {
            wallet.setUsableBalance(wallet.getUsableBalance().subtract(transaction.getAmount()));
        } else if (transaction.getType() == TransactionType.WITHDRAW) {
            wallet.setUsableBalance(wallet.getUsableBalance().add(transaction.getAmount()));
        }
        return walletService.save(wallet);
    }



}
