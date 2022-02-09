package com.example.demo.repository;

import com.example.demo.entity.ClientEntity;

import java.util.List;

public interface ClientCustomRepo {
    List<ClientEntity> findAndSortByCriteria(String criteriaName, String criteriaValue,
                                             String orderBy, String orderDirection);
}
