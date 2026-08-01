package com.swapnil.bankmanagement.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferMoneyDto {
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private BigDecimal amount;
}
