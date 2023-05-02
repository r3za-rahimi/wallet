package com.asan.wallet.services;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.WalletEntity;
import com.asan.wallet.models.dto.Request;
import com.asan.wallet.models.dto.Response;
import com.asan.wallet.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class WalletService extends AbstractService<WalletEntity, WalletRepository> {




    public WalletEntity getWallet(String id) throws ServiceException {

        return repository.findById(id).orElseThrow(() -> new ServiceException("WALLET_NOT_FOUND"));
    }


    public Response getBalance(Request request) {

        WalletEntity wallet = repository.findById(request.getWalletId()).get();

        return new Response(wallet.getBalance());

    }




//    public void withdraw(Request request) throws ServiceException {
//
//
//        WalletEntity wallet = repository.findById(request.getWalletId()).get();
//
//        wallet.setBalance(wallet.getBalance() - request.getAmount());
//
//        int r = getRandomNumber();
//
//        switch (r) {
//
//            case 1 -> {
//                try {
//                    Thread.sleep(60000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            case 2 -> repository.save(wallet);
//            case 3 -> throw new ServiceException("Transaction-Failed");
//        }
//    }
//
//
//    public void deposit(Request request) throws ServiceException {
//
//        WalletEntity wallet = repository.findById(request.getWalletId()).get();
//
//        wallet.setBalance(wallet.getBalance() + request.getAmount());
//
//        int r = getRandomNumber();
//
//        switch (r) {
//
//            case 1 -> {
//                try {
//                    Thread.sleep(6000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            case 2 -> repository.save(wallet);
//            case 3 -> throw new ServiceException("Transaction-Failed");
//        }
//
//    }
//
//





}
