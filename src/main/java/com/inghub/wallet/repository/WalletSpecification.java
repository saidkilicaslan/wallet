package com.inghub.wallet.repository;

import com.inghub.wallet.entity.Currency;
import com.inghub.wallet.entity.Wallet;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class WalletSpecification {

    public static final String AMOUNT = "amount";

    private WalletSpecification() {
    }

    public static Specification<Wallet> withFilters(
            String tckn,
            Currency currency,
            BigDecimal exactAmount,
            BigDecimal minAmount,
            BigDecimal maxAmount) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("customer").get("tckn"), tckn));
            if (currency != null) {
                predicates.add(cb.equal(root.get("currency"), currency));
            }
            if (exactAmount != null) {
                predicates.add(cb.equal(root.get(AMOUNT), exactAmount));
            }
            if (minAmount != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get(AMOUNT), minAmount));
            }
            if (maxAmount != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get(AMOUNT), maxAmount));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
