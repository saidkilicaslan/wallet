package com.inghub.wallet.entity;

public enum TransactionStatus {
    PENDING("Pending"), APPROVED("Approved"), DENIED("Denied");
    private String status;

    TransactionStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
