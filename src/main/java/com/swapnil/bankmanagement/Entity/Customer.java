package com.swapnil.bankmanagement.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Email

    private String email;


    private String phoneNumber;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Account> accounts = new ArrayList<>();

}