package com.asan.wallet.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallet")
@Builder
public class WalletEntity extends AbstractEntity {



    private Long balance;


    @Column(unique = true)
    private String userName;


    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TransactionEntity> transactionEntities;
}
