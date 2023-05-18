package com.asan.wallet.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
