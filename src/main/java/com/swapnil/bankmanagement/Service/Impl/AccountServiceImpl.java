package com.swapnil.bankmanagement.Service.Impl;

import com.swapnil.bankmanagement.Dto.AccountDto;
import com.swapnil.bankmanagement.Dto.CreateAccountDto;
import com.swapnil.bankmanagement.Dto.UpdateAccountDto;
import com.swapnil.bankmanagement.Entity.Account;
import com.swapnil.bankmanagement.Entity.Branch;
import com.swapnil.bankmanagement.Exception.AccountNotFound;
import com.swapnil.bankmanagement.Exception.BranchNotFound;
import com.swapnil.bankmanagement.Repository.AccountRepository;
import com.swapnil.bankmanagement.Repository.BranchRepository;
import com.swapnil.bankmanagement.Repository.UserRepository;
import com.swapnil.bankmanagement.Security.CurrentUserService;
import com.swapnil.bankmanagement.Service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    String getRandomAccountNumber() {
        //Generating an account number
        Long accountNum = secureRandom.nextLong(100_000_000_000L, 999_999_999_999L);

        return String.valueOf(accountNum);
    }

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;
    private final CurrentUserService currentUserService;
    SecureRandom secureRandom = new SecureRandom();

    @Transactional
    @Override
    public AccountDto createAccount(CreateAccountDto createAccountDto) {
        //check Existence
        Branch branch = branchRepository
                .findByBranchName(createAccountDto.getBranchName());


        if (branch == null)
            throw new BranchNotFound("Branch not found");

        //Convert DTO->Entity
        Account account = new Account();
        account.setBalance(createAccountDto.getInitialDeposit());
        account.setBranch(branch);
        account.setCustomer(currentUserService.getCurrentUser());
        account.setAccountNumber(getRandomAccountNumber());

        //Save to DB
        Account savedAccount = accountRepository.save(account);

        //Entity -> DTO
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber(savedAccount.getAccountNumber());
        accountDto.setBalance(savedAccount.getBalance());
        accountDto.setStatus(savedAccount.getStatus());
        accountDto.setBranchId(savedAccount.getBranch().getId());
        accountDto.setCustomerId(savedAccount.getCustomer().getId());
        return accountDto;
    }

    @Transactional
    @Override
    public String deleteAccount(Long accountID) {
        Account account = accountRepository
                .findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Account Not Found With ID: " + accountID));

        accountRepository.deleteById(accountID);
        return "Account Deleted With an ID: " + accountID;
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository
                .findAll()
                .stream()
                .map(n -> modelMapper.map(n, AccountDto.class))
                .toList();
    }

    @Transactional
    @Override
    public AccountDto updateAccount(UpdateAccountDto updateAccountDto, Long accountID) {
        Account account = accountRepository
                .findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Account Not Found With ID: " + accountID));

        modelMapper.map(updateAccountDto, account);
        Account savedAccount = accountRepository.save(account);

        return modelMapper.map(savedAccount, AccountDto.class);
    }

    @Override
    public String checkAccountBalance(String accountNumber) {
        Account account = accountRepository
                .findByAccountNumberAndCustomerEmail(
                        accountNumber
                        , currentUserService.getCurrentUser().getEmail())
                .orElseThrow(() -> new AccountNotFound("Account not Found"));

        return "Your Account Balance is " + account.getBalance();
    }



    @Override
    public List<AccountDto> myAllAccounts() {
        List<Account> accountList = accountRepository
                .findByCustomerEmail(
                        currentUserService
                                .getCurrentUser()
                                .getEmail());

        return accountList.stream()
                .map(n -> modelMapper.map(n,AccountDto.class))
                .toList();
    }
    //this is very important since we are getting user directly from spring security now request body
//    SecurityContext context =
//                SecurityContextHolder.createEmptyContext();
//
//        context.setAuthentication(authentication);
//
//        SecurityContextHolder.setContext(context);
//
//        //create http session - keep authentication persistent

//        request.getSession(true)
//                .setAttribute(
//                        "SPRING_SECURITY_CONTEXT",
//                        context
//                );

}
