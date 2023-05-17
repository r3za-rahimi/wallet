package com.asan.wallet.services;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.dto.UserDetails;
import com.asan.wallet.models.entity.TransactionEntity;
import com.asan.wallet.models.entity.WalletEntity;
import com.asan.wallet.models.enums.DealType;
import com.asan.wallet.models.enums.TrackingStatus;
import com.asan.wallet.models.requestrespons.*;
import com.asan.wallet.repositories.WalletRepository;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    public WalletEntity createWalletFromApi(UserDetails user) throws ServiceException {


        return repository.save(WalletEntity.builder().userName(user.getSub()).balance(0L).build());


    }

    public WalletEntity createWallet(String userName) throws ServiceException {

        return repository.save(WalletEntity.builder().userName(userName).balance(0L).build());

    }

    public BalanceResponse getBalance(UserDetails user) throws ServiceException {

        WalletEntity wallet = getWalletByName(user.getSub());
        return new BalanceResponse(wallet.getBalance());

    }

    @Transactional(rollbackFor = ServiceException.class)
    public WDResponse deposit(WithdrawDepositRequest request, UserDetails user) throws ServiceException {


        WalletEntity wallet = getWalletByName(user.getSub());
        wallet.setBalance(wallet.getBalance() + request.getAmount());

        TransactionEntity transaction = TransactionEntity.builder()
                .amount(request.getAmount())
                .date(new Date())
                .trackingId(request.getTrackingId())
                .dealType(DealType.DEPOSIT)
                .wallet(wallet)
                .build();


        int num = enableTest ? 1 : getRandomNumber();

        switch (num) {
            case 1 -> {
                transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                transactionService.saveTransaction(transaction);

                rabbitService.sendToRabbit(new RabbitRequest(wallet.getId(), user.getToken(), request.getAmount(), DealType.DEPOSIT));
                return new WDResponse(TrackingStatus.SUCCESS);
            }
            case 2 -> {
                transaction.setTrackingStatus(TrackingStatus.FAILED);
                transactionService.saveTransaction(transaction);
                throw new ServiceException("Unknown_Exception");
            }
            case 3 -> {
                try {
                    Thread.sleep(5000);
                    int num2 = getRandomNumber();
                    switch (num2) {
                        case 1 -> {
                            transaction.setTrackingStatus(TrackingStatus.FAILED);
                            transactionService.saveTransaction(transaction);
                            throw new ServiceException("Unknown_Exception");
                        }
                        case 2, 3 -> {
                            transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                            transactionService.saveTransaction(transaction);
                            rabbitService.sendToRabbit(new RabbitRequest(wallet.getId(), user.getToken(), request.getAmount(), DealType.DEPOSIT));
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
    public WDResponse withdraw(WithdrawDepositRequest request,UserDetails user) throws ServiceException {

        WalletEntity wallet = getWalletByName(user.getSub());
        wallet.setBalance(wallet.getBalance() - request.getAmount());

        TransactionEntity transaction = TransactionEntity.builder()
                .amount(request.getAmount())
                .date(new Date())
                .trackingId(request.getTrackingId())
                .dealType(DealType.WITHDRAW)
                .wallet(wallet)
                .build();


        int num = enableTest ? 1 : getRandomNumber();


        switch (num) {
            case 1 -> {
                transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                transactionService.saveTransaction(transaction);
                rabbitService.sendToRabbit(new RabbitRequest(wallet.getId(), user.getToken(), request.getAmount(), DealType.WITHDRAW));
                return new WDResponse(TrackingStatus.SUCCESS);
            }
            case 2 -> {
                transaction.setTrackingStatus(TrackingStatus.FAILED);
                transactionService.saveTransaction(transaction);
                throw new ServiceException("Unknown_Exception");

            }
            case 3 -> {
                try {
                    Thread.sleep(5000);
                    int num2 = getRandomNumber();
                    switch (num2) {
                        case 1 -> {
                            transaction.setTrackingStatus(TrackingStatus.FAILED);
                            transactionService.saveTransaction(transaction);
                            throw new ServiceException("Unknown_Exception");
                        }
                        case 2, 3 -> {
                            transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                            transactionService.saveTransaction(transaction);

                            rabbitService.sendToRabbit(new RabbitRequest(wallet.getId(), user.getToken(), request.getAmount(), DealType.WITHDRAW));
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

        WalletEntity sourceWallet = repository.findByUserName(getUserFromToken(user.getToken()).getSub());
        WalletEntity destinationWallet = repository.findByUserName(request.getUserName());

        sourceWallet.setBalance(sourceWallet.getBalance() - request.getAmount());

        destinationWallet.setBalance(destinationWallet.getBalance() + request.getAmount());


        repository.save(sourceWallet);
        repository.save(destinationWallet);

        return WDResponse.builder().status(TrackingStatus.SUCCESS).build();

    }


    public WalletEntity getWalletByName(String name) throws ServiceException {

        return repository.findByUserName(name);
    }

    private int getRandomNumber() {

        return random.nextInt(3 - 1 + 1) + 1;

    }

    private void safeDeposit(WalletEntity wallet, Long balance) {

        wallet.setBalance(balance);
        repository.save(wallet);
    }

    private UserDetails getUserFromToken(String token) throws ServiceException {

        return jwtService.getAllClaimsFromToken(token);
    }


}
