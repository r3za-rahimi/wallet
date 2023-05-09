package com.asan.wallet.models.dto;

import com.asan.wallet.models.enums.TrackingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {


    private Long balance;

    private TrackingStatus status;


    public Response(TrackingStatus status) {
        this.status = status;
    }

    public Response(Long balance) {
        this.balance = balance;
    }
}
