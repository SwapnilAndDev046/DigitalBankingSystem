package com.swapnil.bankmanagement.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Branch extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String branchName;

    @Column(nullable = false,unique = true)
    private String IFSC_CODE;

    @Column(nullable = false, unique = true)
    private String city;

    @Column(nullable = false, unique = true)
    private String state;

    @OneToMany(mappedBy = "branch")
    @ToString.Exclude
    private List<Account> accounts = new ArrayList<>();

}
