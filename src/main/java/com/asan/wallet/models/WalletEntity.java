package com.asan.wallet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallet")
public class WalletEntity extends AbstractEntity {


    private Long balance;


    private String userName;


    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TransactionEntity> transactionEntities;
}
