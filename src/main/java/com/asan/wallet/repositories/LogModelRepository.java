package com.asan.wallet.repositories;

import com.asan.wallet.aspects.LogModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogModelRepository extends MongoRepository<LogModel , String> {
}
