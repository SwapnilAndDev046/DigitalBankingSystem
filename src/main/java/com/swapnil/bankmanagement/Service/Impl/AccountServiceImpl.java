package com.swapnil.bankmanagement.Service.Impl;

import com.swapnil.bankmanagement.Dto.AccountDto;
import com.swapnil.bankmanagement.Dto.CreateAccountDto;
import com.swapnil.bankmanagement.Dto.UpdateAccountDto;
import com.swapnil.bankmanagement.Entity.Account;
import com.swapnil.bankmanagement.Entity.Branch;
import com.swapnil.bankmanagement.Entity.Customer;
import com.swapnil.bankmanagement.Exception.AccountNotFound;
import com.swapnil.bankmanagement.Exception.CustomerNotFound;
import com.swapnil.bankmanagement.Repository.AccountRepository;
import com.swapnil.bankmanagement.Repository.BranchRepository;
import com.swapnil.bankmanagement.Repository.CustomerRepository;
import com.swapnil.bankmanagement.Service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    String getRandomAccountNumber(){
        //Generating an account number
        Long accountNum = secureRandom.nextLong(100_000_000_000L,999_999_999_999L);

        return String.valueOf(accountNum);
    }
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final BranchRepository branchRepository;
    private final ModelMapper modelMapper;
    SecureRandom secureRandom = new SecureRandom();

    @Transactional
    @Override
    public AccountDto createAccount(CreateAccountDto createAccountDto) {
        //check Existence
        Customer customer = customerRepository
                .findById(createAccountDto.getCustomerId())
                .orElseThrow(()->new EntityNotFoundException
                        ("Customer Not Found With ID:"+createAccountDto.getBranchId()));
        Branch branch = branchRepository
                .findById(createAccountDto.getBranchId())
                .orElseThrow(()->new EntityNotFoundException
                        ("Branch Not Found With ID:"+createAccountDto.getBranchId()));

        //Convert DTO->Entity
        Account account = new Account();
        account.setBalance(createAccountDto.getBalance());
        account.setStatus(createAccountDto.getStatus());
        account.setBranch(branch);
        account.setCustomer(customer);
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
                .orElseThrow(()->new EntityNotFoundException("Account Not Found With ID: "+accountID));

        accountRepository.deleteById(accountID);
        return "Account Deleted With an ID: "+accountID;
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository
                .findAll()
                .stream()
                .map(n-> modelMapper.map(n,AccountDto.class))
                .toList();
    }

    @Transactional
    @Override
    public AccountDto updateAccount(UpdateAccountDto updateAccountDto, Long accountID) {
        Account account = accountRepository
                .findById(accountID)
                .orElseThrow(()->new EntityNotFoundException("Account Not Found With ID: "+accountID));

        modelMapper.map(updateAccountDto,account);
        Account savedAccount = accountRepository.save(account);

        return modelMapper.map(savedAccount,AccountDto.class);
    }

    @Override
    public String checkAccountBalance(String accountNumber) {
        Account account = accountRepository
                .findByAccountNumber(accountNumber);
        if (account==null)
            throw new AccountNotFound("Account Not Found");

        return "Your Account Balance is "+account.getBalance();
    }

    @Override
    public AccountDto findAccountByEmail(String email) {

        Account account = accountRepository.findByCustomerEmail(email);
        if (account==null)
            throw new AccountNotFound("account with this email not available");

        return modelMapper.map(account,AccountDto.class);

    }
}
