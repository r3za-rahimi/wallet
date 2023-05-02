package com.asan.wallet.models.dto.converters;


import com.asan.wallet.models.TransactionEntity;
import com.asan.wallet.models.dto.TransactionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionConverter extends BaseConverter<TransactionEntity , TransactionDto> {

}
