package com.asan.wallet.models.entity;

import com.asan.wallet.models.enums.DealType;
import com.asan.wallet.models.enums.TrackingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WalletTransaction extends AbstractEntity {


    private Date date;

    private Long amount;

    @ManyToOne(cascade = CascadeType.MERGE ,fetch = FetchType.EAGER)
    private WalletEntity wallet;

    @Column(unique = true)
    private String trackingId;

    @Enumerated(EnumType.STRING)
    private TrackingStatus trackingStatus ;

    @Enumerated(EnumType.STRING)
    private DealType dealType;




}
