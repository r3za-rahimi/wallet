package com.asan.wallet.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    @Version
    private Long version;


    @CreatedDate
    private Date insertTimeStamp;

    @LastModifiedDate
    private Date lastUpdateTimeStamp;
}
