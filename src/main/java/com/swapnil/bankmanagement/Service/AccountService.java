package com.swapnil.bankmanagement.Service;

import com.swapnil.bankmanagement.Dto.AccountDto;
import com.swapnil.bankmanagement.Dto.CreateAccountDto;
import com.swapnil.bankmanagement.Dto.UpdateAccountDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(CreateAccountDto createAccountDto);
    String deleteAccount(Long accountID);
    List<AccountDto> getAllAccounts();
    AccountDto updateAccount(UpdateAccountDto updateAccountDto, Long accountID);
    String checkAccountBalance(String accountNumber);
    AccountDto findAccountByEmail(String email);
}
