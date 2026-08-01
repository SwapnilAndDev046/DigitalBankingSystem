package com.swapnil.bankmanagement.Repository;

import com.swapnil.bankmanagement.Entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}