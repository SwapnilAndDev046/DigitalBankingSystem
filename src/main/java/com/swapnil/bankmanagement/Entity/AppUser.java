package com.swapnil.bankmanagement.Entity;

import com.swapnil.bankmanagement.Enum.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.context.support.BeanDefinitionDsl;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")         //Forces it to map to your existing 'customer' table
public class AppUser extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Email
    private String email;

    private String phoneNumber;

    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Roles role = Roles.CUSTOMER;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Account> accounts = new ArrayList<>();

}