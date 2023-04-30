package com.asan.wallet.models.dto.converters;

import com.asan.wallet.models.WalletEntity;
import com.asan.wallet.models.dto.WalletDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface WalletConverter extends BaseConverter<WalletEntity , WalletDto> {


}
