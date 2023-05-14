package com.asan.wallet.services;

import com.asan.wallet.models.entity.TransactionEntity;
import com.asan.wallet.models.requestrespons.TransactionRequest;
import com.asan.wallet.repositories.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TransactionService extends AbstractService<TransactionEntity, TransactionRepository> {


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTransaction(TransactionEntity transaction) {
        repository.save(transaction);

    }

    public List<TransactionEntity> getTransactions(String id) {

        return repository.findByWallet_Id(id);

    }


    public List<TransactionEntity> getTransactionsBetween(TransactionRequest request) {

        return repository.findByWallet_IdAndDateBetween(request.getWalletId() , request.getMinimumDate() , request.getMaximumDate());

    }




}


