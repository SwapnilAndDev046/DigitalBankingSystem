package com.swapnil.bankmanagement.Service.Impl;

import com.swapnil.bankmanagement.Dto.*;
import com.swapnil.bankmanagement.Entity.Account;
import com.swapnil.bankmanagement.Entity.Transaction;
import com.swapnil.bankmanagement.Enum.Status;
import com.swapnil.bankmanagement.Enum.TransactionType;
import com.swapnil.bankmanagement.Exception.AccountNotActive;
import com.swapnil.bankmanagement.Exception.AccountNotFound;
import com.swapnil.bankmanagement.Exception.InvalidAmount;
import com.swapnil.bankmanagement.Exception.LowBalance;
import com.swapnil.bankmanagement.Repository.AccountRepository;
import com.swapnil.bankmanagement.Repository.TransactionRepository;
import com.swapnil.bankmanagement.Service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    String getRandomReference() {
        Long randReference = secureRandom.nextLong(1000, 9999);
        return String.valueOf(randReference);
    }

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;
    SecureRandom secureRandom = new SecureRandom();

    @Transactional
    @Override
    public TransactionDto depositMoney(TransactionOperationDto transactionOperationDto) {
        TransactionDto transactionDto = new TransactionDto();

        //Account Number present or Not
        Account account = accountRepository
                .findByAccountNumber(transactionOperationDto.getAccountNumber());

        //Exceptions
        if (account == null)
            throw new AccountNotFound("This Account Number Don't Exist");
        if (account.getStatus() == Status.CLOSED || account.getStatus() == Status.BLOCKED)
            throw new AccountNotActive("Account is Not Active");

        //Deposit Operation
        account.setBalance(this.deposit(transactionOperationDto.getAmount(),account));
        accountRepository.save(account);

        //InputDto -> Transaction
        Transaction transaction = modelMapper.map(transactionOperationDto, Transaction.class);
        transaction.setAccount(account);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setReferenceId(this.getRandomReference());
        transactionRepository.save(transaction);

        //Transaction -> DTO
        modelMapper.map(transaction, transactionDto);
        transactionDto.setNewBalance(account.getBalance());
        transactionDto.setAccountId(account.getId());
        transactionDto.setAmount(transactionOperationDto.getAmount());

        return transactionDto;
    }

    @Transactional
    @Override
    public TransactionDto withdrawMoney(TransactionOperationDto transactionOperationDto) {
        //Custom JPA Method
        Account account = accountRepository
                .findByAccountNumber(transactionOperationDto.getAccountNumber());

        //Exceptions
        if (account == null)
            throw new AccountNotFound("Account Number Not Found");
        if (account.getStatus() == Status.BLOCKED || account.getStatus() == Status.CLOSED)
            throw new AccountNotActive("Account is Not Active");

        account.setBalance(this.withdraw(transactionOperationDto.getAmount(),account));//this is optional
        accountRepository.save(account);


        //InputDto -> Transaction
        Transaction transaction = modelMapper.map(transactionOperationDto,Transaction.class);
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setAccount(account);
        transaction.setReferenceId(getRandomReference());
        transactionRepository.save(transaction);

        //SavedTransaction -> outputDto
        TransactionDto transactionDto = new TransactionDto();
        modelMapper.map(transaction,transactionDto);
        transactionDto.setAccountId(account.getId());
        transactionDto.setNewBalance(account.getBalance());
        transactionDto.setAmount(transactionOperationDto.getAmount());

        return transactionDto;
    }

    @Transactional
    @Override
    public TransferMoneyResultDto transferMoney(TransferMoneyDto transferMoneyDto) {
        Account senderAccount = accountRepository
                .findByAccountNumber(transferMoneyDto.getSenderAccountNumber());
        if (senderAccount == null)
            throw new AccountNotFound("Account Not Found of Sender");

        Account receiverAccount = accountRepository
                .findByAccountNumber(transferMoneyDto.getReceiverAccountNumber());
        if (receiverAccount == null)
            throw new AccountNotFound("Account Not Found of Receiver");

        //Withdraw from sender
        senderAccount.setBalance(this.withdraw(transferMoneyDto.getAmount(),senderAccount));
        accountRepository.save(senderAccount);

        //Deposit in receiver
        receiverAccount.setBalance(this.deposit(transferMoneyDto.getAmount(),receiverAccount));
        accountRepository.save(receiverAccount);

        //SenderTransaction Operation
        Transaction senderTransaction = new Transaction();
        senderTransaction.setAmount(transferMoneyDto.getAmount());
        senderTransaction.setTransactionType(TransactionType.WITHDRAW);
        senderTransaction.setReferenceId(this.getRandomReference());
        senderTransaction.setAccount(senderAccount);
        transactionRepository.save(senderTransaction);

        //ReceiverTransaction Operation
        Transaction receiverTransaction = new Transaction();
        receiverTransaction.setAmount(transferMoneyDto.getAmount());
        receiverTransaction.setTransactionType(TransactionType.DEPOSIT);
        receiverTransaction.setReferenceId(senderTransaction.getReferenceId());
        receiverTransaction.setAccount(receiverAccount);
        transactionRepository.save(receiverTransaction);

        //ResultDto
        TransferMoneyResultDto transferMoneyResultDto = new TransferMoneyResultDto();
        transferMoneyResultDto.setAmount(transferMoneyDto.getAmount());
        transferMoneyResultDto.setReferenceID(senderTransaction.getReferenceId());
        transferMoneyResultDto.setSenderAccountID(senderAccount.getId());
        transferMoneyResultDto.setReceiverAccountID(receiverAccount.getId());
        transferMoneyResultDto.setSenderNewBalance(senderAccount.getBalance());
        transferMoneyResultDto.setReceiverNewBalance(receiverAccount.getBalance());

        return transferMoneyResultDto;
    }


    //TransactionHistory
    @Override
    public List<TransactionHistoryDto> getAccountHistory(String accountNumber) {
        Account account = accountRepository
                .findByAccountNumber(accountNumber);
        if (account==null)
            throw new AccountNotFound("Account Not Found");

        //Jpa Method
        List<Transaction> transactions = transactionRepository
                .findByAccountId(account.getId());


        return transactions
                .stream()
                .map(n->modelMapper.map(n, TransactionHistoryDto.class))
                .toList();
    }


    BigDecimal withdraw(BigDecimal amount,Account account){
        //Withdraw operation
        // big decimal compare a.compareTo(b) > 0 === a is big than b    a.compareTo(b) < 0 === a is small than b
        if (amount.compareTo(BigDecimal.valueOf(0)) > 0){
            if (amount.compareTo(account.getBalance()) < 0) {
                return account.getBalance().subtract(amount);
            }else {
                throw new LowBalance("Balance is low");
            }
        }else {
            throw new InvalidAmount("Amount Input is Invalid");
        }
    }

    BigDecimal deposit(BigDecimal amount,Account account){
        //Deposit operation
        // big decimal compare a.compareTo(b) > 0 === a is big than b    a.compareTo(b) < 0 === a is small than b
        if (amount.compareTo(BigDecimal.valueOf(0)) > 0){
                return account.getBalance().add(amount);
        }else {
            throw new InvalidAmount("Amount Input is Invalid");
        }
    }


}
