package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "bank_entity")
public class BankEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(unique = true, nullable = false)
  private String name;

  @Column(unique = true, nullable = false)
  private String bankIdentifier;

  public BankEntity() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBankIdentifier() {
    return bankIdentifier;
  }

  public void setBankIdentifier(String bankIdentifier) {
    this.bankIdentifier = bankIdentifier;
  }
}
