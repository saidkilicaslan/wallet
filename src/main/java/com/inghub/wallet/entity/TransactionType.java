package com.inghub.wallet.entity;

public enum TransactionType {
    DEPOSIT ("Deposit"), WITHDRAW("Withdraw");

    private String type;

    TransactionType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
