package com.asan.wallet.models;

import com.asan.wallet.models.enums.DealType;
import com.asan.wallet.models.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity extends AbstractEntity {


    private Date date;

    private Long amount;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private WalletEntity wallet;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private DealType dealType;



}
