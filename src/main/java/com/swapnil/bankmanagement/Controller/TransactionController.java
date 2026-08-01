package com.swapnil.bankmanagement.Controller;

import com.swapnil.bankmanagement.Dto.*;
import com.swapnil.bankmanagement.Service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

private final TransactionService transactionService;

    @PostMapping("/deposit")
    TransactionDto depositMoney(@RequestBody TransactionOperationDto transactionOperationDto){
        return transactionService.depositMoney(transactionOperationDto);
    }

    @PostMapping("/withdraw")
    TransactionDto withdrawMoney(@RequestBody TransactionOperationDto transactionOperationDto){
        return transactionService.withdrawMoney(transactionOperationDto);
    }

    @PostMapping("/transfer")
    TransferMoneyResultDto transferMoney(@RequestBody TransferMoneyDto transferMoneyDto){
        return transactionService.transferMoney(transferMoneyDto);
    }

    //RequestParam - http://localhost:8080/api/v1/transactions/account/history?account-number=348985258178
    @GetMapping("/account/history")
    List<TransactionHistoryDto> getAccountHistory(@RequestParam("account-number") String accountNumber){
        return transactionService.getAccountHistory(accountNumber);
    }
}
