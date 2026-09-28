package com.swapnil.bankmanagement.Repository;

import com.swapnil.bankmanagement.Entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    //Jpa Method
    Branch findByBranchName(String branchName);
}