package com.asan.wallet.repositories;

import com.asan.wallet.models.entity.TransactionEntity;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity , String> {


    List<TransactionEntity> findByWallet_Id(String id);

    List<TransactionEntity> findByWallet_UserNameAndDateBetween(String name , Date d1 , Date d2);


    List<TransactionEntity> findByWallet_UserName(String name);


    TransactionEntity findByTrackingId(String id);
}
