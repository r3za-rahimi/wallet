package com.asan.wallet.models.requestrespons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawDepositRequest {


    private String trackingId;
    private Long amount;


}
