package com.asan.wallet.controllers;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.TransactionEntity;
import com.asan.wallet.models.dto.Request;
import com.asan.wallet.models.dto.TransactionDto;
import com.asan.wallet.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController extends AbstractController<TransactionEntity , TransactionDto , TransactionService> {


    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TransactionDto> getTransaction(@RequestBody Request request) throws ServiceException {

         return converter.convertEntity(service.getTransactions(request.getWalletId()));
    }

    @PostMapping("/transactions/between")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TransactionDto> getTransactionBetween(@RequestBody Request request) throws ServiceException {

        return converter.convertEntity(service.getTransactionsBetween(request));
    }


}
