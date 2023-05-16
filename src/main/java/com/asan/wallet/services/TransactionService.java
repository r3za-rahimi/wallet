package com.asan.wallet.services;

import com.asan.wallet.exceptionhandler.exceptions.ServiceException;
import com.asan.wallet.models.entity.TransactionEntity;
import com.asan.wallet.models.requestrespons.TransactionRequest;
import com.asan.wallet.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService extends AbstractService<TransactionEntity, TransactionRepository> {

    @Autowired
    JwtService jwtService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveTransaction(TransactionEntity transaction) {
        repository.save(transaction);

    }

    public List<TransactionEntity> getTransactions(String token) throws ServiceException {

        return repository.findByWallet_UserName(jwtService.getAllClaimsFromToken(token).getSub());

    }


    public List<TransactionEntity> getTransactionsBetween(TransactionRequest request , String token) throws ServiceException {

        return repository.findByWallet_UserNameAndDateBetween(jwtService.getAllClaimsFromToken(token).getSub(), request.getMinimumDate(), request.getMaximumDate());

    }


}


