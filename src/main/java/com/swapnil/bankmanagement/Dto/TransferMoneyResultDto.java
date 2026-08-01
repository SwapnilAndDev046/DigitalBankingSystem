package com.swapnil.bankmanagement.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferMoneyResultDto {
    private Long senderAccountID;
    private Long receiverAccountID;
    private BigDecimal amount;
    private String referenceID;
    private BigDecimal senderNewBalance;
    private BigDecimal receiverNewBalance;
}
