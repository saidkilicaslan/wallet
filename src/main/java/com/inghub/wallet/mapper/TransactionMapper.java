package com.inghub.wallet.mapper;

import com.inghub.wallet.entity.Transaction;
import com.inghub.wallet.model.TransactionRequest;
import com.inghub.wallet.model.TransactionResponse;

public class TransactionMapper {

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

    public static Transaction toEntity(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setOppositeParty(transactionRequest.getSource());
        transaction.setOppositePartyType(transactionRequest.getSourceType());
        return transaction;
    }
}
