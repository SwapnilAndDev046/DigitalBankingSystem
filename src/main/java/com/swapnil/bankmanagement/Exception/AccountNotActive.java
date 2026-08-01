package com.swapnil.bankmanagement.Exception;

public class AccountNotActive extends RuntimeException {
    public AccountNotActive(String message) {
        super(message);
    }
}
