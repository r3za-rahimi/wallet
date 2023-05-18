package com.asan.wallet.models.requestrespons;

import com.asan.wallet.models.enums.DealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RabbitRequest {


    private String walletId;
    private String token;
    private Long amount;
    private Date date;
    private DealType dealType;



}
