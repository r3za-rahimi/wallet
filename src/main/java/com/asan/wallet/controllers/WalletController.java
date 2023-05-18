package com.asan.wallet.controllers;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.entity.WalletEntity;
import com.asan.wallet.models.dto.*;
import com.asan.wallet.models.requestrespons.*;
import com.asan.wallet.services.JwtService;
import com.asan.wallet.services.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WalletController extends AbstractController<WalletEntity, WalletDto, WalletService> {

    @Autowired
    JwtService jwtService;

    @PostMapping()
    public ResponseEntity<String> createWallet(@RequestHeader("Authorization") String token ) throws ServiceException {

        WalletEntity walletEntity = service.createWallet(jwtService.getAllClaimsFromToken(token));
        return new ResponseEntity<>(walletEntity.getId(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/balance")
    public BalanceResponse getBalance(@RequestBody BalanceRequest request , @RequestHeader("Authorization") String token) throws ServiceException {
        return service.getBalance(request , jwtService.getAllClaimsFromToken(token));
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public WDResponse withdraw(@RequestBody WithdrawDepositRequest request , @RequestHeader("Authorization") String token) throws ServiceException {

        return service.withdraw(request ,jwtService.getAllClaimsFromToken(token));
    }

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public WDResponse deposit(@RequestBody WithdrawDepositRequest request , @RequestHeader("Authorization") String token) throws ServiceException {

        return service.deposit(request ,jwtService.getAllClaimsFromToken(token));
    }

    @PostMapping("/toWallet")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public WDResponse toWallet(@RequestBody WalletRequest request , @RequestHeader("Authorization") String token) throws ServiceException {

        return service.walletToWallet(request ,jwtService.getAllClaimsFromToken(token));

    }


    @GetMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<WalletDto> walletList( @RequestHeader("Authorization") String token) throws ServiceException {

        return converter.convertEntity(service.getWalletsByUsername(jwtService.getAllClaimsFromToken(token).getSub()));

    }

}
