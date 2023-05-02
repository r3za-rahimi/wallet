package com.asan.wallet.models.dto;

import com.asan.wallet.models.TransactionEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WalletDto extends AbstractDto {

    private Long balance;

    private String userName;

    private List<TransactionDto> transactions;

}
