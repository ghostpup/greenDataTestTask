package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


@Entity
public class DepositEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private Long clientIdentifier;
  @Column(nullable = false)
  private Long bankIdentifier;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private java.util.Date openDate;
  @Column(nullable = false)
  private Integer percentage;
  @Column(nullable = false)
  private Integer monthTerm;

  public DepositEntity() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getClientIdentifier() {
    return clientIdentifier;
  }

  public void setClientIdentifier(Long clientIdentifier) {
    this.clientIdentifier = clientIdentifier;
  }

  public Long getBankIdentifier() {
    return bankIdentifier;
  }

  public void setBankIdentifier(Long bankIdentifier) {
    this.bankIdentifier = bankIdentifier;
  }

  public Date getOpenDate() {
    return openDate;
  }

  public void setOpenDate(Date openDate) {
    this.openDate = openDate;
  }

  public Integer getPercentage() {
    return percentage;
  }

  public void setPercentage(Integer percentage) {
    this.percentage = percentage;
  }

  public Integer getMonthTerm() {
    return monthTerm;
  }

  public void setMonthTerm(Integer monthTerm) {
    this.monthTerm = monthTerm;
  }
}
