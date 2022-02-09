package com.example.demo.repository;

import com.example.demo.entity.BankEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankRepo extends CrudRepository<BankEntity, Long>, BankCustomRepo {

    BankEntity findByName(String name);

    BankEntity findByBankIdentifier(String bank_identifier);
    BankEntity findByIdAndNameAndBankIdentifier(Long id, String name, String bank_identifier);
    @Query(value = "SELECT b" +
            " FROM bank_entity b" +
            " WHERE (bank_identifier = :bank_identifier OR name = :name) AND id != :id")
    List<BankEntity> findAllByBankIdentifierOrNameAndIdNot(@Param("id") Long id,
                                                           @Param("bank_identifier") String bank_identifier,
                                                           @Param("name") String name);
}
