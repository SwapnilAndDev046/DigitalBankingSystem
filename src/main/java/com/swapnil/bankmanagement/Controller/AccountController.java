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

    @PostMapping
    AccountDto createAccount(@RequestBody CreateAccountDto createAccountDto) {
        return accountService.createAccount(createAccountDto);
    }

    @DeleteMapping("/{id}")
    String deleteAccount(@PathVariable Long id){
        return accountService.deleteAccount(id);
    }

    @GetMapping
    List<AccountDto> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PutMapping("/{id}")
    AccountDto updateAccountStatus(@RequestBody UpdateAccountDto updateAccountDto, @PathVariable Long id){
        return accountService.updateAccount(updateAccountDto,id);
    }

    @GetMapping("/balance")
    String checkAccountBalance(@RequestParam("accountNumber")String accountNumber){
        return accountService.checkAccountBalance(accountNumber);
    }
    @GetMapping("/account")
    AccountDto findAccountByEmail(@RequestParam("email")String email){
        return accountService.findAccountByEmail(email);
    }

}
