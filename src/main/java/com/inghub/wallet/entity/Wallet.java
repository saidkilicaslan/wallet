package com.inghub.wallet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "wallet")
@Setter
@Getter
public class Wallet {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "wallet_id", unique = true)
    private UUID walletId;

    @ManyToOne
    private Customer customer;

    @Column(name = "wallet_name")
    private String walletName;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "balance", precision = 19, scale = 2)
    private BigDecimal balance;

    @Column(name = "active_for_shopping")
    private Boolean activeForShopping;

    @Column(name = "active_for_withdraw")
    private Boolean activeForWithdraw;

    @Column(name = "usable_balance", precision = 19, scale = 2)
    private BigDecimal usableBalance;

    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactions;
}
