package com.inghub.wallet.controller;

import com.inghub.wallet.api.DepositApi;
import com.inghub.wallet.mapper.TransactionMapper;
import com.inghub.wallet.model.TransactionRequest;
import com.inghub.wallet.model.TransactionResponse;
import com.inghub.wallet.service.DepositService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DepositApiController implements DepositApi {
    private final DepositService depositService;
    @Override
    public ResponseEntity<TransactionResponse> deposit(@Valid  @RequestBody TransactionRequest request) {
        return new ResponseEntity<>(TransactionMapper.toResponse(depositService.deposit(request)), HttpStatus.OK);
    }
}
