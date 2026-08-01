package com.swapnil.bankmanagement.Dto;

import com.swapnil.bankmanagement.Enum.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionHistoryDto {
    private BigDecimal amount;
    private Long accountId;
    private LocalDateTime transactionTime;
    private String referenceId;
    private TransactionType transactionType;
}
