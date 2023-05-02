package com.asan.wallet.models.dto;

import com.asan.wallet.models.enums.DealType;
import com.asan.wallet.models.enums.TrackingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto extends AbstractDto {


    private Date date;
    private Long amount;
    private WalletDto wallet;
    private TrackingStatus trackingStatus;
    private DealType dealType;

}
