package com.asan.wallet.models.requestrespons;

import com.asan.wallet.models.enums.TrackingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WDResponse {

    private TrackingStatus status;


}
