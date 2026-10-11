package com.swapnil.bankmanagement.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
}
