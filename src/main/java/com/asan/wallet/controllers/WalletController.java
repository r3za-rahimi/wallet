package com.asan.wallet.controllers;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.WalletEntity;
import com.asan.wallet.models.dto.Request;
import com.asan.wallet.models.dto.Response;
import com.asan.wallet.models.dto.WalletDto;
import com.asan.wallet.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletController extends AbstractController<WalletEntity, WalletDto, WalletService> {


    @PostMapping
    public void createWallet(@RequestBody WalletDto walletDto) throws ServiceException {

        service.insert(converter.convertDto(walletDto));

    }

    @PostMapping("/balance")
    public Response getBalance(@RequestBody Request request) throws ServiceException {

        return service.getBalance(request);
    }

        @PostMapping("/withdraw")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public Response withdraw(@RequestBody Request request) throws ServiceException {

           return service.withdraw(request);
        }

        @PostMapping("/deposit")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public Response deposit(@RequestBody Request request) throws ServiceException {

            return service.deposit(request);

        }


}
