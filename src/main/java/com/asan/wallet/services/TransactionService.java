package com.asan.wallet.services;

import com.asan.wallet.models.TransactionEntity;
import com.asan.wallet.models.WalletEntity;
import com.asan.wallet.models.dto.Request;
import com.asan.wallet.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService extends AbstractService<TransactionEntity , TransactionRepository>{

    public List<TransactionEntity> getTransactions(Request request) {

        WalletEntity wallet = repository.findById(request.getWalletId()).get();

        return wallet.getTransactionEntities();

    }

}


