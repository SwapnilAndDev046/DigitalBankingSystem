package com.swapnil.bankmanagement.Repository;

import com.swapnil.bankmanagement.Entity.Account;
import com.swapnil.bankmanagement.Enum.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {


    Account findByAccountNumber(@Param("accountNumber") String accountNumber);

    List<Account> findByCustomerEmail(String email);

    Optional<Account> findByAccountNumberAndCustomerEmail(String accountNumber, String email);
}