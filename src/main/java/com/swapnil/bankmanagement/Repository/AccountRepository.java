package com.swapnil.bankmanagement.Repository;

import com.swapnil.bankmanagement.Entity.Account;
import com.swapnil.bankmanagement.Enum.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("Select a from Account a where a.AccountNumber =:AccountNumber")
    Account findByAccountNumber(@Param("AccountNumber") String AccountNumber);

    Account findByCustomerEmail(String email);
}