package com.swapnil.bankmanagement.Dto;

import com.swapnil.bankmanagement.Enum.Roles;
import lombok.Data;

@Data
public class CurrentUserResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Roles role;
}
