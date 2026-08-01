package com.swapnil.bankmanagement.Dto;

import com.swapnil.bankmanagement.Enum.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class TransactionDto {
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal NewBalance;
    private LocalDateTime transactionTime;
    private Long accountId;
    private String referenceId;

    public TransactionDto(TransactionType transactionType, BigDecimal amount, LocalDateTime transactionTime, Long id, String referenceId) {
    }
}
