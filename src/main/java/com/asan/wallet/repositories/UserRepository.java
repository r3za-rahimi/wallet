package com.asan.wallet.repositories;

import com.asan.wallet.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity , Long> {
}
