package com.asan.wallet.models.dto;

import lombok.Data;

@Data
public class UserDto extends AbstractDto{

    private String phoneNumber;

    private String password;


}
