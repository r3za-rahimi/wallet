package com.asan.wallet.repositories;

import com.asan.wallet.models.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity , String> {


    List<TransactionEntity> findByWallet_Id(String id);
}
