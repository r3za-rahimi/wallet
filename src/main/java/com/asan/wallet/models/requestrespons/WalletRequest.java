package com.asan.wallet.models.requestrespons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletRequest {

    private String userName;
    private Long amount;

}
