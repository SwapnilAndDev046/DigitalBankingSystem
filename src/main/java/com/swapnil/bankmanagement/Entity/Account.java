package com.swapnil.bankmanagement.Entity;

import com.swapnil.bankmanagement.Enum.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false,nullable = false, unique = true)
    private String accountNumber;

    @Column
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private AppUser customer;

    @ManyToOne
    @JoinColumn(nullable = false,name = "branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    private List<Transaction> transactions = new ArrayList<>();



}
