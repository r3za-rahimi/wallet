package com.asan.wallet.repositories;

import com.asan.wallet.models.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletEntity , Long> {



}
