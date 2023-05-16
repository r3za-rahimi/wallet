package com.asan.wallet.repositories;

import com.asan.wallet.models.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletEntity , String> {
    WalletEntity findByUserName(String userName);

}
