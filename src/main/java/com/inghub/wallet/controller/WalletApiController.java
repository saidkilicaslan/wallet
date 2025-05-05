package com.inghub.wallet.controller;

import com.inghub.wallet.api.WalletApi;
import com.inghub.wallet.entity.Currency;
import com.inghub.wallet.entity.Wallet;
import com.inghub.wallet.exception.ApiException;
import com.inghub.wallet.exception.IllegalRequestParamException;
import com.inghub.wallet.mapper.WalletMapper;
import com.inghub.wallet.model.WalletCreate;
import com.inghub.wallet.model.WalletResponse;
import com.inghub.wallet.service.WalletService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
public class WalletApiController implements WalletApi {
    private final WalletService walletService;

    @Override
    public ResponseEntity<WalletResponse> createWallet(@Valid @RequestBody WalletCreate walletCreateRequest) {
        return new ResponseEntity<>(WalletMapper.toModel(walletService.createWallet(WalletMapper.toEntity(walletCreateRequest), walletCreateRequest.getTckn())), HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<List<WalletResponse>> listWallets(@PathVariable("tckn") String tckn,
                                                            @RequestParam(name = "currency", required = false) Currency currency,
                                                            @RequestParam(name = "exactAmount", required = false) BigDecimal exactAmount,
                                                            @RequestParam(name = "minAmount", required = false) BigDecimal minAmount,
                                                            @RequestParam(name = "maxAmount", required = false) BigDecimal maxAmount,
                                                            @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "10") Integer size, HttpServletResponse response) throws ApiException {
        if (page < 0 || size <= 0) {
            throw new IllegalRequestParamException("Page number and size must be greater than 0");
        }
        Page<Wallet> walletPage = walletService.listWallets(tckn, currency, exactAmount, minAmount, maxAmount, page, size);
        if (walletPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (walletPage.getTotalPages() > 1) {
            response.setHeader("X-Total-Count", String.valueOf(walletPage.getTotalElements()));
            response.setHeader("X-Total-Pages", String.valueOf(walletPage.getTotalPages()));
            response.setHeader("X-Current-Page", String.valueOf(page));
            response.setHeader("X-Page-Size", String.valueOf(size));
            return new ResponseEntity<>(walletPage.getContent().stream().map(WalletMapper::toModel).toList(), HttpStatus.PARTIAL_CONTENT);
        }
        return new ResponseEntity<>(walletPage.getContent().stream().map(WalletMapper::toModel).toList(), HttpStatus.OK);
    }


}
