package com.asan.wallet.repositories;

import com.asan.wallet.models.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<WalletEntity , String> {
    List<WalletEntity> findByUserName(String userName);

}
