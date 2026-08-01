package com.swapnil.bankmanagement.Exception;

public class AccountNotFound extends RuntimeException {
    public AccountNotFound(String message) {
        super(message);
    }
}
