package com.swapnil.bankmanagement.Controller;

import com.swapnil.bankmanagement.Dto.*;
import com.swapnil.bankmanagement.Service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<TransactionDto> depositMoney(@RequestBody TransactionOperationDto transactionOperationDto){
        return ResponseEntity.ok(transactionService.depositMoney(transactionOperationDto));
    }

//    @PostMapping("/withdraw")
//    TransactionDto withdrawMoney(@RequestBody TransactionOperationDto transactionOperationDto){
//        return transactionService.withdrawMoney(transactionOperationDto);
//    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferMoneyResultDto> transferMoney(@RequestBody TransferMoneyDto transferMoneyDto){
        return ResponseEntity.ok(transactionService.transferMoney(transferMoneyDto));
    }

    @GetMapping("/{accountNumber}/history")
    public ResponseEntity<List<TransactionHistoryResponseDto>> getAccountHistory(@PathVariable String accountNumber){
        return ResponseEntity.ok(transactionService.getAccountHistory(accountNumber));
    }
}
