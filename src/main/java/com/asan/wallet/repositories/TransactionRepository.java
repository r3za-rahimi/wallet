package com.asan.wallet.repositories;

import com.asan.wallet.models.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<WalletTransaction, String> {


    List<WalletTransaction> findByWallet_Id(String id);

    List<WalletTransaction> findByWallet_UserNameAndDateBetween(String name , Date d1 , Date d2);


    List<WalletTransaction> findByWallet_UserName(String name);


    WalletTransaction findByTrackingId(String id);
}
