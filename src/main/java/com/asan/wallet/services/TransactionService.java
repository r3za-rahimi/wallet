package com.asan.wallet.services;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.TransactionEntity;
import com.asan.wallet.models.WalletEntity;
import com.asan.wallet.models.dto.Request;
import com.asan.wallet.models.dto.converters.BaseConverter;
import com.asan.wallet.models.enums.DealType;
import com.asan.wallet.models.enums.TrackingStatus;
import com.asan.wallet.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
@Service
public class TransactionService extends AbstractService<TransactionEntity, TransactionRepository> {


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTransaction(TransactionEntity transaction) {
        System.out.println(transaction.getTrackingStatus());
        repository.save(transaction);

    }

    public List<TransactionEntity> getTransactions(String id) {

        return repository.findByWallet_Id(id);

    }


    public List<TransactionEntity> getTransactionsBetween(Request request) {

        return repository.findByWallet_IdAndDateBetween(request.getWalletId() , request.getMinimumDate() , request.getMaximumDate());


    }




}


