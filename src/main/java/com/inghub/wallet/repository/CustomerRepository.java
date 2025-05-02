package com.inghub.wallet.repository;

import com.inghub.wallet.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByTckn(String tckn);
    Optional<Customer> findByTckn(String tckn);
}
