package com.example.demo.repository;

import com.example.demo.entity.BankEntity;

import java.util.List;

public interface BankCustomRepo {

    List<BankEntity> findAndSortByCriteria(String criteriaName, String criteriaValue,
                                           String orderBy, String orderDirection);
}
