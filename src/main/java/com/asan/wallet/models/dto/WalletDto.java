package com.asan.wallet.models.dto;

import com.asan.wallet.models.TransactionEntity;
import lombok.Data;

import java.util.List;

@Data
public class WalletDto extends AbstractDto {

    private Long balance;

    private UserDto user;

    private List<TransactionDto> transactions;

}
