package com.swapnil.bankmanagement;

import com.swapnil.bankmanagement.Dto.CustomerDto;
import com.swapnil.bankmanagement.Repository.AccountRepository;
import com.swapnil.bankmanagement.Repository.CustomerRepository;
import com.swapnil.bankmanagement.Service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class customerTest {
    @Autowired
    private CustomerService customerService;
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void createCustomer(){
//        CustomerDto customerDto = CustomerDto
//                .name("swapnil devkate")
//                .email("swap@gmail.com")
//                .phoneNumber("9292929292")
//                .build();
//        System.out.println(customerService.createCustomer(customerDto));
    }

    @Test
    public void findAccount(){
        System.out.println(accountRepository
                .findByAccountNumber("454896166922"));
    }
}
