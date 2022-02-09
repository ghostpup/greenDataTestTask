package com.example.demo.service;

import com.example.demo.entity.ClientEntity;
import com.example.demo.exception.client.ClientSortOrFilterParamsNotValid;
import com.example.demo.exception.client.*;
import com.example.demo.repository.ClientRepo;
import com.example.demo.repository.DepositRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;

@Service
public class ClientService {

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private DepositRepo depositRepo;

    public ClientEntity create(@RequestBody ClientEntity clientEntity) throws ClientParameterIsNull, ClientNameAlreadyExists, ClientAdressAlreadyOccupied, ClientOrgFormIsNotValid {

        if(clientEntity.getName() == null)
            throw new ClientParameterIsNull("Значение параметра name равно null. Ожидалась строка.");

        if(clientEntity.getShortName() == null)
            throw new ClientParameterIsNull("Значение параметра shortName равно null. Ожидалась строка.");

        if(clientEntity.getAdress() == null)
            throw new ClientParameterIsNull("Значение параметра adress равно null. Ожидалась строка.");

        if(clientEntity.getOrgForm() == null)
            throw new ClientParameterIsNull("Значение параметра orgForm равно null. Ожидалась строка.");

        if(clientRepo.findByName(clientEntity.getName()) != null)
            throw new ClientNameAlreadyExists("Клиент с таким полным именем уже существует");

        if(clientRepo.findByAdress(clientEntity.getAdress())!=null)
            throw new ClientAdressAlreadyOccupied("Этот адрес уже занят. Два клиента/юр. лица не могу находится по одному адресу.");

        if(clientEntity.getOrgForm()<0 || clientEntity.getOrgForm() > 30)
            throw new ClientOrgFormIsNotValid("Идентификатор организационно-правовой формы не найден.");

        return clientRepo.save(clientEntity);
    }

    public ClientEntity update(@RequestBody ClientEntity clientEntity) throws ClientParameterIsNull, ClientNameAndAdressAlreadyExistsCombined, ClientOrgFormIsNotValid {

        if(clientEntity.getName() == null)
            throw new ClientParameterIsNull("Значение параметра name равно null. Ожидалась строка.");

        if(clientEntity.getShortName() == null)
            throw new ClientParameterIsNull("Значение параметра shortName равно null. Ожидалась строка.");

        if(clientEntity.getAdress() == null)
            throw new ClientParameterIsNull("Значение параметра adress равно null. Ожидалась строка.");

        if(clientEntity.getOrgForm() == null)
            throw new ClientParameterIsNull("Значение параметра orgForm равно null. Ожидалась строка.");

        if(!checkSameValuesInOtherEntries(clientEntity))
            throw new ClientNameAndAdressAlreadyExistsCombined("В системе уже присутствуют клиенты с таким именем и/или адресом");

        if(clientEntity.getOrgForm()<0 || clientEntity.getOrgForm() > 30)
            throw new ClientOrgFormIsNotValid("Идентификатор организационно-правовой формы не найден.");

        return clientRepo.save(clientEntity);
    }

    public Iterable<ClientEntity> getClients(String criteriaName, String criteriaValue,
                                             String orderBy, String orderDirection) throws ClientSortOrFilterParamsNotValid {
        String[] fields={"id","name","shortName","adress","orgForm"};
        if((!criteriaName.isEmpty() && !Arrays.asList(fields).contains(criteriaName)) ||
                (!orderBy.isEmpty() && !Arrays.asList(fields).contains(orderBy))){
            throw new ClientSortOrFilterParamsNotValid("Неверные названия параметров фильтрации и сортировки. Доступные значения: "+ Arrays.toString(fields));
        }
        return clientRepo.findAndSortByCriteria(criteriaName, criteriaValue, orderBy, orderDirection);
    };

    public void deleteById(@RequestBody Long id) throws ClientHasDeposit{
        if(depositRepo.findByClientIdentifier(id) != null)
            throw new ClientHasDeposit("Вы не можете удалить клиента, т.к. у него присутсвуют активные вклады.");
        clientRepo.deleteById(id);
    }

    private Boolean checkSameValuesInOtherEntries(ClientEntity clientEntity){
        return clientRepo.findAllByNameOrAdressAndIdNot(
                        clientEntity.getId(),
                        clientEntity.getAdress(),
                        clientEntity.getName())
                .isEmpty();
    }
}
