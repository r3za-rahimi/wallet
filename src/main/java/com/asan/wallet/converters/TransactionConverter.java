package com.asan.wallet.converters;


import com.asan.wallet.models.entity.WalletTransaction;
import com.asan.wallet.models.dto.TransactionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionConverter extends BaseConverter<WalletTransaction, TransactionDto> {

}
