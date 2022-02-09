package com.example.demo.repository;

import com.example.demo.entity.DepositEntity;

import java.util.List;

public interface DepositCustomRepo {

    List<DepositEntity> findAndSortByCriteria(String criteriaName, String criteriaValue,
                                              String orderBy, String orderDirection);
}
