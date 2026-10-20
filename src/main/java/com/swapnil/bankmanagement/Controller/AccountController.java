package com.swapnil.bankmanagement.Controller;

import com.swapnil.bankmanagement.Dto.AccountDto;
import com.swapnil.bankmanagement.Dto.CreateAccountDto;
import com.swapnil.bankmanagement.Dto.UpdateAccountDto;
import com.swapnil.bankmanagement.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody CreateAccountDto createAccountDto) {
        return ResponseEntity.ok(accountService.createAccount(createAccountDto));
    }

    @GetMapping("/my-accounts")
    public ResponseEntity<List<AccountDto>> myAllAccounts(){
        return ResponseEntity.ok(accountService.myAllAccounts());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccountStatus(@RequestBody UpdateAccountDto updateAccountDto, @PathVariable Long id){
        return ResponseEntity.ok(accountService.updateAccount(updateAccountDto,id));
    }

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<String> checkAccountBalance(@PathVariable String accountNumber){
        return ResponseEntity.ok(accountService.checkAccountBalance(accountNumber));
    }


}
