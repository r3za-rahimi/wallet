package com.asan.wallet.controllers;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.entity.WalletEntity;
import com.asan.wallet.models.dto.*;
import com.asan.wallet.models.requestrespons.*;
import com.asan.wallet.services.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletController extends AbstractController<WalletEntity, WalletDto, WalletService> {


    @PostMapping()
    public ResponseEntity<String> createWallet(@RequestBody CreateWalletRequest wallet) throws ServiceException {

        WalletEntity walletEntity = service.createWallet(wallet);
        return new ResponseEntity<>(walletEntity.getId(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/balance")
    public BalanceResponse getBalance(@RequestBody BalanceRequest request) throws ServiceException {
        return service.getBalance(request);
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public WDResponse withdraw(@RequestBody WithdrawDepositRequest request) throws ServiceException {

        return service.withdraw(request);
    }

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public WDResponse deposit(@RequestBody WithdrawDepositRequest request) throws ServiceException {

        return service.deposit(request);

    }


}
