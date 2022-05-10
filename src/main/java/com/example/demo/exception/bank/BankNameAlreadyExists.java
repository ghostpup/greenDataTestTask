package com.example.demo.exception.bank;

public class BankNameAlreadyExists extends Exception {
  public BankNameAlreadyExists(String message) {
    super(message);
  }
}
