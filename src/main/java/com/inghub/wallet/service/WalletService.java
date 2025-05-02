package com.inghub.wallet.service;

import com.inghub.wallet.entity.Currency;
import com.inghub.wallet.entity.Wallet;
import com.inghub.wallet.exception.ApiException;
import com.inghub.wallet.exception.ResultNotFoundException;
import com.inghub.wallet.repository.WalletRepository;
import com.inghub.wallet.repository.WalletSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final CustomerService customerService;

    public Wallet createWallet(Wallet wallet, String tckn) throws ApiException {
        wallet.setWalletId(UUID.randomUUID());
        wallet.setCustomer(customerService.getCustomerByTckn(tckn));
        return walletRepository.save(wallet);
    }

    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    public Wallet findWalletForUpdate(UUID walletId) throws ApiException {
        return walletRepository.findByWalletIdForUpdate(walletId)
                .orElseThrow(() -> new ResultNotFoundException("Wallet not found"));

    }

    public Page<Wallet> listWallets(String tckn,
                                    Currency currency,
                                    BigDecimal exactAmount,
                                    BigDecimal minAmount,
                                    BigDecimal maxAmount,
                                    Integer page,
                                    Integer size) {
        Specification<Wallet> spec = WalletSpecification.withFilters(tckn, currency, exactAmount, minAmount, maxAmount);
        return walletRepository.findAll(spec, PageRequest.of(page, size));
    }
}
