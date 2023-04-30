package com.asan.wallet.services;

import com.asan.wallet.exceptionHandler.exceptions.ServiceException;
import com.asan.wallet.models.TransactionEntity;
import com.asan.wallet.models.WalletEntity;
import com.asan.wallet.models.dto.Request;
import com.asan.wallet.models.dto.Response;
import com.asan.wallet.models.dto.TransactionDto;
import com.asan.wallet.models.dto.WalletDto;
import com.asan.wallet.models.dto.converters.BaseConverter;
import com.asan.wallet.repositories.WalletRepository;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class WalletService extends AbstractService<WalletEntity, WalletRepository> {




    Random random = new Random();


    public Response getBalance(Request request) {

        WalletEntity wallet = repository.findById(request.getWalletId()).get();

        return new Response(request.getToken(), wallet.getBalance());

    }


    public void withdraw(Request request) throws ServiceException {


        WalletEntity wallet = repository.findById(request.getWalletId()).get();

        wallet.setBalance(wallet.getBalance() - request.getAmount());

        int r = getRandomNumber();

        switch (r) {

            case 1 -> {
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            case 2 -> repository.save(wallet);
            case 3 -> throw new ServiceException("Transaction-Failed");
        }
    }


    public void deposit(Request request) throws ServiceException {

        WalletEntity wallet = repository.findById(request.getWalletId()).get();

        wallet.setBalance(wallet.getBalance() + request.getAmount());

        int r = getRandomNumber();

        switch (r) {

            case 1 -> {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            case 2 -> repository.save(wallet);
            case 3 -> throw new ServiceException("Transaction-Failed");
        }

    }



    private int getRandomNumber() {

        return random.nextInt(3 - 1 + 1) + 1;

    }


}
