package com.swapnil.bankmanagement.Exception;

public class LowBalance extends RuntimeException {
    public LowBalance(String message) {
        super(message);
    }
}
