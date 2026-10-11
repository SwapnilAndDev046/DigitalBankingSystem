package com.swapnil.bankmanagement.Dto;

import lombok.Data;

@Data
public class BranchDto {
    private Long id;
    private String branchName;
    private String IFSC_CODE;
    private String city;
    private String state;
}
