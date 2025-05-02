package com.inghub.wallet.exception;

public class IllegalTransactionStatusException extends ApiException{
    public IllegalTransactionStatusException(String message) {
        super(message);
    }
}
