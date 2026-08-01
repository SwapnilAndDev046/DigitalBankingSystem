package com.swapnil.bankmanagement.Repository;

import com.swapnil.bankmanagement.Entity.Customer;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);

    @Query(value = "SELECT * FROM customer",nativeQuery = true)
    Page<Customer> findCustomerWithLimit(Pageable pageable);
}