package com.swapnil.bankmanagement.Repository;

import com.swapnil.bankmanagement.Entity.Transaction;
import com.swapnil.bankmanagement.Enum.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

        List<Transaction> findByAccountId(Long Id);
}