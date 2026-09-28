package com.swapnil.bankmanagement.Dto;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
}
