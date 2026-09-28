package com.swapnil.bankmanagement.Controller;

import com.swapnil.bankmanagement.Dto.AccountDto;
import com.swapnil.bankmanagement.Dto.CreateAccountDto;
import com.swapnil.bankmanagement.Dto.UpdateAccountDto;
import com.swapnil.bankmanagement.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    AccountDto createAccount(@RequestBody CreateAccountDto createAccountDto) {
        return accountService.createAccount(createAccountDto);
    }

    @GetMapping("/my-accounts")
    List<AccountDto> myAllAccounts(){
        return accountService.myAllAccounts();
    }

    @DeleteMapping("/{id}")
    String deleteAccount(@PathVariable Long id){
        return accountService.deleteAccount(id);
    }

    @GetMapping("/all")
    List<AccountDto> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PutMapping("/{id}")
    AccountDto updateAccountStatus(@RequestBody UpdateAccountDto updateAccountDto, @PathVariable Long id){
        return accountService.updateAccount(updateAccountDto,id);
    }

    @GetMapping("/{accountNumber}/balance")
    String checkAccountBalance(@PathVariable String accountNumber){
        return accountService.checkAccountBalance(accountNumber);
    }


}
