package com.asan.wallet.controllers;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.TransactionEntity;
import com.asan.wallet.models.dto.Request;
import com.asan.wallet.models.dto.TransactionDto;
import com.asan.wallet.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController extends AbstractController<TransactionEntity , TransactionDto , TransactionService> {



    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void withdraw(@RequestBody Request request) throws ServiceException {

//        service.withdraw(request);
    }


    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deposit(@RequestBody Request request) throws ServiceException {

        service.deposit(request);

    }
    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TransactionDto> getTransaction(@RequestBody Request request) throws ServiceException {

         return converter.convertEntity(service.getTransactions(request.getWalletId()));
    }


}
