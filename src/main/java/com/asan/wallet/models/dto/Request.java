package com.asan.wallet.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {


    private String walletId;
    private Long amount;
    private Date minimumDate;
    private Date maximumDate;
}
