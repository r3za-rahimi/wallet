package com.asan.wallet.models.requestrespons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Request {


    private String walletId;
    private String trackingId;
    private Long amount;
    private Date minimumDate;
    private Date maximumDate;

}
