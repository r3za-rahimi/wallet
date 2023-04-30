package com.asan.wallet.repositories;

import com.asan.wallet.models.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity , Long> {
}
