package com.asan.wallet.services;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.dto.UserDetails;
import com.asan.wallet.models.entity.WalletTransaction;
import com.asan.wallet.models.entity.WalletEntity;
import com.asan.wallet.models.enums.DealType;
import com.asan.wallet.models.enums.TrackingStatus;
import com.asan.wallet.models.requestrespons.*;
import com.asan.wallet.repositories.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class WalletService extends AbstractService<WalletEntity, WalletRepository> {

    @Value("${app.test}")
    private Boolean enableTest;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    JwtService jwtService;

    @Autowired
    private RabbitService rabbitService;


    private Random random = new Random();

    public WalletEntity createWallet(UserDetails user) throws ServiceException {
        return repository.save(WalletEntity.builder().userName(user.getSub()).balance(getRandomNumber() * 1000L).build());
    }

    public BalanceResponse getBalance(BalanceRequest request, UserDetails user) throws ServiceException {

        WalletEntity wallet = repository.findById(request.getWalletId()).orElseThrow(() -> new ServiceException("Wallet_NOT_FOUND"));
        return new BalanceResponse(wallet.getBalance());

    }

    @Transactional(rollbackFor = ServiceException.class)
    public WDResponse deposit(WithdrawDepositRequest request, UserDetails user) throws ServiceException {

        if ( !enableTest &  !validateWalletWithUsername(user.getSub() ,request.getWalletId() ))
            throw new ServiceException("SECURITY_EXCEPTION");

        WalletEntity wallet = repository.findById(request.getWalletId()).orElseThrow(() -> new ServiceException("Wallet_NOT_FOUND"));
        wallet.setBalance(wallet.getBalance() + request.getAmount());


        WalletTransaction transaction;

        if (transactionService.getTransactionByTrk(request.getTrackingId()) == null){
            transaction = WalletTransaction.builder()
                    .amount(request.getAmount())
                    .date(new Date())
                    .trackingId(request.getTrackingId())
                    .dealType(DealType.DEPOSIT)
                    .wallet(wallet)
                    .build();
        }else {

            throw new ServiceException("TRACK_ID_EXCEPTION");
        }

        int num = enableTest ? 1 : getRandomNumber();

        switch (num) {
            case 1 -> {
                transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                transactionService.saveTransaction(transaction);
                rabbitService.sendToRabbit(new RabbitRequest(wallet.getId(), user.getToken(), request.getAmount(), new Date(), DealType.DEPOSIT));
                return new WDResponse(TrackingStatus.SUCCESS);
            }
            case 2 -> {

                return new WDResponse(TrackingStatus.FAILED);
            }
            case 3 -> {
                try {
                    Thread.sleep(5000);
                    int num2 = getRandomNumber();
                    switch (num2) {
                        case 1 -> {
                            return new WDResponse(TrackingStatus.FAILED);
                        }
                        case 2, 3 -> {
                            transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                            transactionService.saveTransaction(transaction);
                            rabbitService.sendToRabbit(new RabbitRequest(wallet.getId(), user.getToken(), request.getAmount(), new Date(), DealType.DEPOSIT));
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
    public WDResponse withdraw(WithdrawDepositRequest request, UserDetails user) throws ServiceException {

        if (!enableTest  &  !validateWalletWithUsername(user.getSub() ,request.getWalletId() ))
            throw new ServiceException("SECURITY_EXCEPTION");


        WalletEntity wallet = repository.findById(request.getWalletId()).orElseThrow(() -> new ServiceException("Wallet_NOT_FOUND"));
        wallet.setBalance(wallet.getBalance() - request.getAmount());

        WalletTransaction transaction;

        if (transactionService.getTransactionByTrk(request.getTrackingId()) == null){
            transaction = WalletTransaction.builder()
                    .amount(request.getAmount())
                    .date(new Date())
                    .trackingId(request.getTrackingId())
                    .dealType(DealType.WITHDRAW)
                    .wallet(wallet)
                    .build();
        }else {

            throw new ServiceException("TRACK_ID_EXCEPTION");
        }


        int num = enableTest ? 1 : getRandomNumber();


        switch (num) {
            case 1 -> {
                transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                transactionService.saveTransaction(transaction);
                rabbitService.sendToRabbit(new RabbitRequest(wallet.getId(), user.getToken(), request.getAmount(), new Date(), DealType.WITHDRAW));
                return new WDResponse(TrackingStatus.SUCCESS);
            }
            case 2 -> {
                return new WDResponse(TrackingStatus.FAILED);
            }
            case 3 -> {
                try {
                    Thread.sleep(5000);
                    int num2 = getRandomNumber();
                    switch (num2) {
                        case 1 -> {
                            return new WDResponse(TrackingStatus.FAILED);
                        }
                        case 2, 3 -> {
                            transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                            transactionService.saveTransaction(transaction);
                            rabbitService.sendToRabbit(new RabbitRequest(wallet.getId(), user.getToken(), request.getAmount(), new Date(), DealType.WITHDRAW));
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
    public WDResponse walletToWallet(WalletRequest request, UserDetails user) throws ServiceException {

        if ( !enableTest  &  !validateWalletWithUsername(user.getSub() ,request.getSourceWalletId() ))
            throw new ServiceException("SECURITY_EXCEPTION");

        WalletEntity sourceWallet = repository.findById(request.getSourceWalletId()).orElseThrow(() -> new ServiceException("Wallet_NOT_FOUND"));
        WalletEntity destinationWallet = repository.findById(request.getDestinationWalletId()).orElseThrow(() -> new ServiceException("Wallet_NOT_FOUND"));

        if (sourceWallet.getBalance() < request.getAmount()) {
            throw new ServiceException("lOW_BALANCE");
        }
        sourceWallet.setBalance(sourceWallet.getBalance() - request.getAmount());
        destinationWallet.setBalance(destinationWallet.getBalance() + request.getAmount());


        repository.save(sourceWallet);
        repository.save(destinationWallet);

        return WDResponse.builder().status(TrackingStatus.SUCCESS).build();

    }


    public List<WalletEntity> getWalletsByUsername(String userName) {

        return repository.findByUserName(userName);

    }

    private Boolean validateWalletWithUsername(String username, String walletId) {

        List<WalletEntity> walletList = getWalletsByUsername(username);
        System.out.println(walletList.size());

        for (WalletEntity wallet : walletList) {

            if (wallet.getId().equals(walletId))
                return true;
        }
        return false;
    }


    private int getRandomNumber() {

        return random.nextInt(3 - 1 + 1) + 1;

    }


    private UserDetails getUserFromToken(String token) throws ServiceException {

        return jwtService.getAllClaimsFromToken(token);
    }


}
