package com.asan.wallet.services;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.TransactionEntity;
import com.asan.wallet.models.WalletEntity;
import com.asan.wallet.models.dto.Request;
import com.asan.wallet.models.dto.Response;
import com.asan.wallet.models.enums.DealType;
import com.asan.wallet.models.enums.TrackingStatus;
import com.asan.wallet.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class WalletService extends AbstractService<WalletEntity, WalletRepository> {


    @Autowired
    TransactionService transactionService;

    Random random = new Random();

    public WalletEntity getWallet(String id) throws ServiceException {

        return repository.findById(id).orElseThrow(() -> new ServiceException("WALLET_NOT_FOUND"));
    }


    public Response getBalance(Request request) {

        WalletEntity wallet = repository.findById(request.getWalletId()).get();

        return new Response(wallet.getBalance());

    }

    @Transactional(rollbackFor = ServiceException.class)
    public void deposit(Request request) throws ServiceException {


        WalletEntity wallet = getWallet(request.getWalletId());
        wallet.setBalance(wallet.getBalance() + request.getAmount());

        TransactionEntity transaction = TransactionEntity.builder()
                .amount(request.getAmount())
                .date(new Date())
                .trackingId(request.getTrackingId())
                .dealType(DealType.DEPOSIT)
                .wallet(wallet)
                .build();


        int num = getRandomNumber();

        switch (num) {
            case 1 -> {
                transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                transactionService.saveTransaction(transaction);

            }
            case 2 -> {
                transaction.setTrackingStatus(TrackingStatus.FAILED);
                transactionService.saveTransaction(transaction);

                throw new ServiceException("Unknown_EXEPTION");

            }
            case 3 -> {
                try {
                    Thread.sleep(10000);
                    int num2 = getRandomNumber();
                    switch (num2) {
                        case 1 -> {
                            transaction.setTrackingStatus(TrackingStatus.FAILED);
                            transactionService.saveTransaction(transaction);
                            throw new ServiceException("Unknown_EXEPTION");
                        }
                        case 2, 3 -> {
                            transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                            transactionService.saveTransaction(transaction);
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }


    }

    @Transactional(rollbackFor = ServiceException.class)
    public void withdraw(Request request) throws ServiceException {


        WalletEntity wallet = getWallet(request.getWalletId());
        wallet.setBalance(wallet.getBalance() - request.getAmount());

        TransactionEntity transaction = TransactionEntity.builder()
                .amount(request.getAmount())
                .date(new Date())
                .trackingId(request.getTrackingId())
                .dealType(DealType.DEPOSIT)
                .wallet(wallet)
                .build();


        int num = getRandomNumber();

        switch (num) {
            case 1 -> {
                transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                transactionService.saveTransaction(transaction);

            }
            case 2 -> {
                transaction.setTrackingStatus(TrackingStatus.FAILED);
                transactionService.saveTransaction(transaction);

                throw new ServiceException("Unknown_EXEPTION");

            }
            case 3 -> {
                try {
                    Thread.sleep(10000);
                    int num2 = getRandomNumber();
                    switch (num2) {
                        case 1 -> {
                            transaction.setTrackingStatus(TrackingStatus.FAILED);
                            transactionService.saveTransaction(transaction);
                            throw new ServiceException("Unknown_EXEPTION");
                        }
                        case 2, 3 -> {
                            transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                            transactionService.saveTransaction(transaction);
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }


    }


    private int getRandomNumber() {

        return random.nextInt(3 - 1 + 1) + 1;

    }

/** generate TrackingID*/
//    private String generateID() {
//        return UUID.randomUUID().toString();
//    }


}
