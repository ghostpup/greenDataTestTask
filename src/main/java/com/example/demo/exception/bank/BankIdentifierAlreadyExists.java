package com.example.demo.exception.bank;

public class BankIdentifierAlreadyExists extends Exception{
    public BankIdentifierAlreadyExists(String message) {
        super(message);
    }
}
