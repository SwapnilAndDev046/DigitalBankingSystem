package com.swapnil.bankmanagement.Exception;

public class CustomerNotFound extends RuntimeException {
    public CustomerNotFound(String message) {
        super(message);
    }
}
