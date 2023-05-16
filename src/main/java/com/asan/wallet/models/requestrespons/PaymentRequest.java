package com.asan.wallet.models.requestrespons;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentRequest {

    @Pattern(regexp = "\\d{16}" , message = "Card Number Must Have 16 Digit")
    private String cardNumber;

    private Integer cvv2;

    @Pattern(regexp = "\\d{2}-\\d{2}" , message = "expire time must be like 05-04")
    private String expireTime;

    private Integer otp;

    private Long amount;

}
