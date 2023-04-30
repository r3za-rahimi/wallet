package com.asan.wallet.models.dto.converters;

import com.asan.wallet.models.UserEntity;
import com.asan.wallet.models.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConverter extends BaseConverter<UserEntity , UserDto>{

}
