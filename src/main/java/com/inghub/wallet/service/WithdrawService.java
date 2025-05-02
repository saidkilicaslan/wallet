package com.inghub.wallet.service;

import com.inghub.wallet.entity.OppositePartyType;
import com.inghub.wallet.entity.Transaction;
import com.inghub.wallet.entity.TransactionType;
import com.inghub.wallet.entity.Wallet;
import com.inghub.wallet.exception.ApiException;
import com.inghub.wallet.exception.WalletIsNotAvailableForUsageException;
import com.inghub.wallet.model.TransactionRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class WithdrawService {
    private final WalletService walletService;
    private final TransactionService transactionService;

    @Transactional
    public Transaction withdraw(TransactionRequest transactionRequest) throws ApiException {
        Wallet wallet = walletService.findWalletForUpdate(transactionRequest.getWalletId());
        if (wallet.getUsableBalance().compareTo(transactionRequest.getAmount()) < 0 || wallet.getBalance().compareTo(transactionRequest.getAmount()) < 0) {
            throw new WalletIsNotAvailableForUsageException("Insufficient balance");
        }
        if(!wallet.getActiveForShopping() && OppositePartyType.PAYMENT_ID.equals(transactionRequest.getSourceType())){
            throw new WalletIsNotAvailableForUsageException("Wallet is not available for shopping");
        }
        if(!wallet.getActiveForWithdraw() && OppositePartyType.IBAN.equals(transactionRequest.getSourceType())){
            throw new WalletIsNotAvailableForUsageException("Wallet is not available for withdraw");
        }
        wallet.setBalance(wallet.getBalance().subtract(transactionRequest.getAmount()));
        if (transactionRequest.getAmount().compareTo(BigDecimal.valueOf(1000)) <= 0) {
            wallet.setUsableBalance(wallet.getUsableBalance().subtract(transactionRequest.getAmount()));
        }
        return  transactionService.createTransaction(walletService.save(wallet), transactionRequest, TransactionType.WITHDRAW);
    }
}
