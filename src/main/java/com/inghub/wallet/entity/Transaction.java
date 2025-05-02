package com.inghub.wallet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallet_transaction")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "transaction_id", unique = true)
    private UUID transactionId;

    @ManyToOne
    private Wallet wallet;

    @Column(name = "amount", precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_type")
    private TransactionType type;

    @Column(name = "opposite_party_type")
    private OppositePartyType oppositePartyType;

    @Column(name = "opposite_party")
    private String oppositeParty;

    @Column(name = "transaction_status")
    private TransactionStatus status;
}
