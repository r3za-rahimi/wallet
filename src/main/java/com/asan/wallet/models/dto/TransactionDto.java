package com.asan.wallet.models.dto;

import com.asan.wallet.models.enums.DealType;
import com.asan.wallet.models.enums.TransactionType;


import java.util.Date;

public class TransactionDto extends AbstractDto {


    private Date date;

    private Long amount;
    private WalletDto wallet;

    private TransactionType transactionType;

    private DealType dealType;

}
