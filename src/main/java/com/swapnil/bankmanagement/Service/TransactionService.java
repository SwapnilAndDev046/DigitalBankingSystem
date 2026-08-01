package com.swapnil.bankmanagement.Service;

import com.swapnil.bankmanagement.Dto.*;

import java.util.List;

public interface TransactionService {
    TransactionDto depositMoney(TransactionOperationDto transactionOperationDto);
    TransactionDto withdrawMoney(TransactionOperationDto transactionOperationDto);
    TransferMoneyResultDto transferMoney(TransferMoneyDto transferMoneyDto);
    List<TransactionHistoryDto> getAccountHistory(String accountNumber);
}
