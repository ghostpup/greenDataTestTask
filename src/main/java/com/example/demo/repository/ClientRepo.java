package com.example.demo.repository;

import com.example.demo.entity.ClientEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepo extends CrudRepository<ClientEntity, Long>, ClientCustomRepo {

    ClientEntity  findByName(String name);

    ClientEntity  findByAdress(String adress);

    @Query(value = "SELECT c" +
            " FROM client_entity c" +
            " WHERE (name = :name OR adress = :adress) AND id != :id")
    List<ClientEntity> findAllByNameOrAdressAndIdNot(@Param("id") Long id,
                                                           @Param("adress") String adress,
                                                           @Param("name") String name);

}
