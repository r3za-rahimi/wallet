package com.asan.wallet.models.requestrespons;

import com.asan.wallet.models.enums.DealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RabbitRequest {


    private String walletId;
    private Long amount;
    private DealType dealType;

}
