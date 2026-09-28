package com.swapnil.bankmanagement.Repository;

import com.swapnil.bankmanagement.Entity.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);

    @Query(value = "SELECT * FROM customer",nativeQuery = true)
    Page<AppUser> findCustomerWithLimit(Pageable pageable);

}