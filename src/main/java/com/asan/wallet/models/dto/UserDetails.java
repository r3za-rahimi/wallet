package com.asan.wallet.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDetails {

    private String sub;
    private String role;
    @JsonIgnore
    private String token;
//    private String accountNumber;
//    private String exp;
//    private String iat;
//    private String cardNumber;
//    private String jti;


}
