package com.asan.wallet.converters;


import com.asan.wallet.models.entity.TransactionEntity;
import com.asan.wallet.models.dto.TransactionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionConverter extends BaseConverter<TransactionEntity , TransactionDto> {

}
