package com.asan.wallet.repositories;

import com.asan.wallet.models.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity , String> {


    List<TransactionEntity> findByWallet_Id(String id);

    List<TransactionEntity> findByWallet_IdAndDateBetween(String id , Date d1 , Date d2);
}
