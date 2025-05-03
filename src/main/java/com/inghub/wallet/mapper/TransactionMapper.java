package com.inghub.wallet.mapper;

import com.inghub.wallet.entity.Transaction;
import com.inghub.wallet.model.TransactionResponse;

public final class TransactionMapper {
    private TransactionMapper() {
    }

    public static TransactionResponse toResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setTransactionId(transaction.getTransactionId());
        response.setAmount(transaction.getAmount());
        response.setSource(transaction.getOppositeParty());
        response.setStatus(transaction.getStatus());
        response.setWalletId(transaction.getWallet().getWalletId());
        response.setSourceType(transaction.getOppositePartyType());
        return response;
    }
}
