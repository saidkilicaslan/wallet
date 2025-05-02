package com.inghub.wallet.controller;

import com.inghub.wallet.api.WithdrawApi;
import com.inghub.wallet.mapper.TransactionMapper;
import com.inghub.wallet.model.TransactionRequest;
import com.inghub.wallet.model.TransactionResponse;
import com.inghub.wallet.service.WithdrawService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WithdrawApiController implements WithdrawApi {
    private final WithdrawService withdrawService;
    @Override
    public ResponseEntity<TransactionResponse> withdraw(TransactionRequest request) {
        return new ResponseEntity<>(TransactionMapper.toResponse(withdrawService.withdraw(request)), HttpStatus.OK);
    }
}
