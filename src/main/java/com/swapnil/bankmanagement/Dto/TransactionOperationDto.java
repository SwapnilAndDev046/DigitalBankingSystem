package com.swapnil.bankmanagement.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionOperationDto {
    private BigDecimal amount;
    private String accountNumber;
}
