package com.swapnil.bankmanagement.Dto;

import com.swapnil.bankmanagement.Enum.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountDto {
    private BigDecimal initialDeposit;
    private String branchName;
}
