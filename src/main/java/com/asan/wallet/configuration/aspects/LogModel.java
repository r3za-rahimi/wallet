package com.asan.wallet.configuration.aspects;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "wallet")
public class LogModel {

    @MongoId
    private String id;
    private String methodName;
    private Object request;
    private Object response;
    private String errorTrace;
}
