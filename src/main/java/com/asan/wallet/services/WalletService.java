package com.asan.wallet.services;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.entity.TransactionEntity;
import com.asan.wallet.models.entity.WalletEntity;
import com.asan.wallet.models.enums.DealType;
import com.asan.wallet.models.enums.TrackingStatus;
import com.asan.wallet.models.requestrespons.*;
import com.asan.wallet.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

@Service
public class WalletService extends AbstractService<WalletEntity, WalletRepository> {


    @Autowired
    private TransactionService transactionService;

    @Autowired
    private RabbitService rabbitService;

    private Random random = new Random();


    public WalletEntity createWallet(CreateWalletRequest request) throws ServiceException {

        WalletEntity wallet =  repository.save(WalletEntity.builder().userName(request.getUserName()).build());
        safeDeposit(wallet , request.getBalance());
        return wallet;

    }

    public BalanceResponse getBalance(BalanceRequest request) throws ServiceException {

        WalletEntity wallet = getWallet(request.getWalletId());
        return new BalanceResponse(wallet.getBalance());

    }

    @Transactional(rollbackFor = ServiceException.class)
    public WDResponse deposit(WithdrawDepositRequest request) throws ServiceException {

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
                rabbitService.sendToRabbit(new RabbitRequest(wallet.getId(), request.getAmount(),DealType.DEPOSIT));
                return new WDResponse(TrackingStatus.SUCCESS);

            }
            case 2 -> {

                transaction.setTrackingStatus(TrackingStatus.FAILED);
                transactionService.saveTransaction(transaction);

                throw  new ServiceException("Unknown_Exception");



            }
            case 3 -> {
                try {
                    Thread.sleep(5000);
                    int num2 = getRandomNumber();
                    switch (num2) {
                        case 1 -> {
                            transaction.setTrackingStatus(TrackingStatus.FAILED);
                            transactionService.saveTransaction(transaction);
                            throw  new ServiceException("Unknown_Exception");
                        }
                        case 2, 3 -> {
                            transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                            transactionService.saveTransaction(transaction);
                            rabbitService.sendToRabbit(new RabbitRequest(wallet.getId(), request.getAmount(),DealType.DEPOSIT));
                            return new WDResponse(TrackingStatus.SUCCESS);
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        }

        throw new ServiceException("Unknown_Exception");

    }

    @Transactional(rollbackFor = ServiceException.class)
    public WDResponse withdraw(WithdrawDepositRequest request) throws ServiceException {


        WalletEntity wallet = getWallet(request.getWalletId());
        wallet.setBalance(wallet.getBalance() - request.getAmount());

        TransactionEntity transaction = TransactionEntity.builder()
                .amount(request.getAmount())
                .date(new Date())
                .trackingId(request.getTrackingId())
                .dealType(DealType.WITHDRAW)
                .wallet(wallet)
                .build();


        int num = getRandomNumber();

        switch (num) {
            case 1 -> {
                transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                transactionService.saveTransaction(transaction);
                rabbitService.sendToRabbit(new RabbitRequest(wallet.getId(), request.getAmount(),DealType.WITHDRAW));
                return new WDResponse(TrackingStatus.SUCCESS);

            }
            case 2 -> {
                transaction.setTrackingStatus(TrackingStatus.FAILED);
                transactionService.saveTransaction(transaction);
                throw  new ServiceException("Unknown_Exception");

            }
            case 3 -> {
                try {
                    Thread.sleep(5000);
                    int num2 = getRandomNumber();
                    switch (num2) {
                        case 1 -> {
                            transaction.setTrackingStatus(TrackingStatus.FAILED);
                            transactionService.saveTransaction(transaction);
                            throw  new ServiceException("Unknown_Exception");
                        }
                        case 2, 3 -> {
                            transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                            transactionService.saveTransaction(transaction);
                            rabbitService.sendToRabbit(new RabbitRequest(wallet.getId(), request.getAmount(),DealType.WITHDRAW));
                            return new WDResponse(TrackingStatus.SUCCESS);
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }

        throw new ServiceException("Unknown_Exception");
    }


    public WalletEntity getWallet(String id) throws ServiceException {

        return repository.findById(id).orElseThrow(() -> new ServiceException("WALLET_NOT_FOUND"));
    }

    private int getRandomNumber() {

        return random.nextInt(3 - 1 + 1) + 1;

    }


    private void safeDeposit(WalletEntity wallet , Long balance ){

        wallet.setBalance(balance);
        repository.save(wallet);
    }


}
