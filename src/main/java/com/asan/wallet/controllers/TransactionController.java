package com.asan.wallet.controllers;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.entity.TransactionEntity;
import com.asan.wallet.models.dto.TransactionDto;
import com.asan.wallet.models.requestrespons.TransactionRequest;
import com.asan.wallet.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController extends AbstractController<TransactionEntity , TransactionDto , TransactionService> {


    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TransactionDto> getTransaction(@RequestBody TransactionRequest request) throws ServiceException {

         return converter.convertEntity(service.getTransactions(request.getWalletId()));
    }

    @PostMapping("/transactions/between")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TransactionDto> getTransactionBetween(@RequestBody TransactionRequest request) throws ServiceException {

        return converter.convertEntity(service.getTransactionsBetween(request));
    }


}
