package com.asan.wallet.controllers;

import com.asan.wallet.exceptionHandler.exceptions.ServiceException;
import com.asan.wallet.models.WalletEntity;
import com.asan.wallet.models.dto.Request;
import com.asan.wallet.models.dto.Response;
import com.asan.wallet.models.dto.WalletDto;
import com.asan.wallet.services.AbstractService;
import com.asan.wallet.services.WalletService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletController extends AbstractController<WalletEntity, WalletDto, WalletService> {


    @PostMapping
    public void createWallet(@RequestBody WalletDto walletDto) {

        service.insert(converter.convertDto(walletDto));

    }

    @PostMapping("/balance")
    public Response gwtBalance(@RequestBody Request request){

        return service.getBalance(request);
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void withdraw(@RequestBody Request request) throws ServiceException {

        service.withdraw(request);
    }


    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void diposite(@RequestBody Request request) throws ServiceException {

        service.deposit(request);
    }



}
