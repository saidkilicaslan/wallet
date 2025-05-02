package com.inghub.wallet.controller;

import com.inghub.wallet.api.TransactionApi;
import com.inghub.wallet.entity.Transaction;
import com.inghub.wallet.entity.TransactionStatus;
import com.inghub.wallet.exception.IllegalRequestParamException;
import com.inghub.wallet.mapper.TransactionMapper;
import com.inghub.wallet.model.TransactionResponse;
import com.inghub.wallet.service.TransactionLifecycleService;
import com.inghub.wallet.service.TransactionService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class TransactionApiController implements TransactionApi {
    private final TransactionService transactionService;
    private final TransactionLifecycleService transactionLifecycleService;

    @Override
    public ResponseEntity<List<TransactionResponse>> listTransactions(UUID walletId, Integer page, Integer size, HttpServletResponse response) {
        if (page < 0 || size <= 0) {
            throw new IllegalRequestParamException("Page number and size must be greater than 0");
        }
        Page<Transaction> transactionPage = transactionService.listTransactions(walletId, page, size);
        if (transactionPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (transactionPage.getTotalPages() > 1) {
            response.setHeader("X-Total-Count", String.valueOf(transactionPage.getTotalElements()));
            response.setHeader("X-Total-Pages", String.valueOf(transactionPage.getTotalPages()));
            response.setHeader("X-Current-Page", String.valueOf(page));
            response.setHeader("X-Page-Size", String.valueOf(size));
            return new ResponseEntity<>(transactionPage.getContent().stream().map(TransactionMapper::toResponse).toList(), HttpStatus.PARTIAL_CONTENT);
        }
        return new ResponseEntity<>(transactionPage.getContent().stream().map(TransactionMapper::toResponse).toList(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionResponse> approve(@PathVariable("transactionId") UUID transactionId) {
        return new ResponseEntity<>(TransactionMapper.toResponse(transactionLifecycleService.updateStatus(transactionId, TransactionStatus.APPROVED)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionResponse> deny(@PathVariable("transactionId") UUID transactionId) {
        return new ResponseEntity<>(TransactionMapper.toResponse(transactionLifecycleService.updateStatus(transactionId, TransactionStatus.DENIED)), HttpStatus.OK);

    }
}
