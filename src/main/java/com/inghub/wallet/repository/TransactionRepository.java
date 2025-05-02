package com.inghub.wallet.repository;

import com.inghub.wallet.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionId(UUID transactionId);
    Page<Transaction> findAllByWallet_walletId(UUID walletId, Pageable pageable);
}
