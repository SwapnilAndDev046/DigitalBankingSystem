package com.swapnil.bankmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BankManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankManagementApplication.class, args);
        System.out.println("Running.....");
    }

}
