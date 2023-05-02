package com.asan.wallet.services;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.TransactionEntity;
import com.asan.wallet.models.WalletEntity;
import com.asan.wallet.models.dto.Request;
import com.asan.wallet.models.enums.DealType;
import com.asan.wallet.models.enums.TrackingStatus;
import com.asan.wallet.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class TransactionService extends AbstractService<TransactionEntity, TransactionRepository> {

    @Autowired
    private WalletService walletService;

    Random random = new Random();

    @Transactional(rollbackFor = ServiceException.class)
    public void deposit(Request request) throws ServiceException {


        WalletEntity wallet = walletService.getWallet(request.getWalletId());
        wallet.setBalance(wallet.getBalance() + request.getAmount());

        TransactionEntity transaction = TransactionEntity.builder()
                .amount(request.getAmount())
                .date(new Date())
                .trackingId(generateID())
                .dealType(DealType.DEPOSIT)
                .wallet(wallet)
                .build();


        int num = getRandomNumber();

        switch (num) {
            case 1 -> {
                transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                saveTransaction(transaction);

            }
            case 2 -> {
                transaction.setTrackingStatus(TrackingStatus.FAILED);
                saveTransaction(transaction);

                throw new ServiceException("Unknown_EXEPTION");

            }
            case 3 -> {
                try {
                    Thread.sleep(10000);
                    int num2 = getRandomNumber();
                    switch (num2) {
                        case 1 -> {
                            transaction.setTrackingStatus(TrackingStatus.FAILED);
                            saveTransaction(transaction);

                            throw new ServiceException("Unknown_EXEPTION");
                        }
                        case 2, 3 -> {
                            transaction.setTrackingStatus(TrackingStatus.SUCCESS);
                            saveTransaction(transaction);

                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }


    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public void saveTransaction(TransactionEntity transaction) {
        System.out.println(transaction.getTrackingStatus());
        repository.save(transaction);

    }

    public List<TransactionEntity> getTransactions(String id) {

        return repository.findByWallet_Id(id);

    }


    private int getRandomNumber() {

        return random.nextInt(2 - 1 + 1) + 1;

    }

    private String generateID() {
        return UUID.randomUUID().toString();
    }

}


