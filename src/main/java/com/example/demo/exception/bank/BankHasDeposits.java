package com.example.demo.exception.bank;

public class BankHasDeposits extends Exception{
    public BankHasDeposits(String message) {
        super(message);
    }
}
