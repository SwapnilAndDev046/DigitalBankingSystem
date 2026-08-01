package com.swapnil.bankmanagement.Dto;

import com.swapnil.bankmanagement.Enum.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountDto {
    private BigDecimal balance;
    private Status status;
    private Long customerId;
    private Long branchId;
}
