package com.asan.wallet.models.dto;

import lombok.Data;
import java.util.Date;

@Data
public class AbstractDto {

    private String id;

    private Long version;

    private Date insertTimeStamp;

    private Date lastUpdateTimeStamp;
}
