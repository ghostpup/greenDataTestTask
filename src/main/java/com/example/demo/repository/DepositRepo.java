package com.example.demo.repository;

import com.example.demo.entity.DepositEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface DepositRepo extends CrudRepository<DepositEntity, Long>, DepositCustomRepo{
    DepositEntity findByBankIdentifier(Long bank_identifier);
    DepositEntity findByClientIdentifier(Long client_identifier);
    DepositEntity findByClientIdentifierAndBankIdentifier(Long client_identifier,Long bank_identifier);
}
