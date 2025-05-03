package com.inghub.wallet.exception;

public class InvalidTransactionAmountException extends ApiException {
    public InvalidTransactionAmountException(String message) {
        super(message);
    }
}
