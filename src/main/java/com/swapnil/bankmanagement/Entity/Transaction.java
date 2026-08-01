package com.swapnil.bankmanagement.Entity;

import com.swapnil.bankmanagement.Enum.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
public class Transaction extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private BigDecimal amount;

    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime transactionTime;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(updatable = false,nullable = false)
    private String referenceId;

}
