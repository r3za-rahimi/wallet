package com.asan.wallet.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {


    private String token;

    private Long walletId;

    private Long amount;


}
