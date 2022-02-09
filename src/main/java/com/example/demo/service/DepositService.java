package com.example.demo.service;

import com.example.demo.entity.DepositEntity;
import com.example.demo.exception.client.ClientSortOrFilterParamsNotValid;
import com.example.demo.exception.deposit.*;
import com.example.demo.repository.BankRepo;
import com.example.demo.repository.ClientRepo;
import com.example.demo.repository.DepositRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Optional;

@Service
public class DepositService {
    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private BankRepo bankRepo;

    @Autowired
    private DepositRepo depositRepo;

    public DepositEntity create(@RequestBody DepositEntity depositEntity) throws DepositParameterIsNull, DepositClientIdentifierNotFound, DepositBankIdentifierNotFound, DepositMonthTermNotValid, DepositPercentageNotValid {

        depositEntity.setOpenDate(new Timestamp(System.currentTimeMillis()));

        checkAllRulesForAllParams(depositEntity);

        return depositRepo.save(depositEntity);
    }

    public DepositEntity update(@RequestBody DepositEntity depositEntity) throws DepositPercentageNotValid, DepositBankIdentifierNotFound, DepositMonthTermNotValid, DepositParameterIsNull, DepositClientIdentifierNotFound, DepositNotFound {

        Optional<DepositEntity> oldEntity = depositRepo.findById(depositEntity.getId());
        if(oldEntity.isPresent())
            depositEntity.setOpenDate(oldEntity.get().getOpenDate());
        else
            throw new DepositNotFound("Вклад с таким id не найден");

        checkAllRulesForAllParams(depositEntity);

        return depositRepo.save(depositEntity);
    }

    public void deleteById(@RequestBody Long id){
        depositRepo.deleteById(id);
    }

    public Iterable<DepositEntity> getDeposits(String criteriaName, String criteriaValue,
                                               String orderBy, String orderDirection) throws DepositSortOrFilterParamsNotValid {
        String[] fields={"id","clientIdentifier","bankIdentifier","percentage","monthTerm"};
        if((!criteriaName.isEmpty() && !Arrays.asList(fields).contains(criteriaName)) ||
                (!orderBy.isEmpty() && !Arrays.asList(fields).contains(orderBy))){
            throw new DepositSortOrFilterParamsNotValid("Неверные названия параметров фильтрации и сортировки. Доступные значения: "+ Arrays.toString(fields));
        }
        return depositRepo.findAndSortByCriteria(criteriaName, criteriaValue, orderBy, orderDirection);
    }

    private void checkAllRulesForAllParams(DepositEntity depositEntity) throws DepositParameterIsNull, DepositBankIdentifierNotFound, DepositClientIdentifierNotFound, DepositMonthTermNotValid, DepositPercentageNotValid {
        if(depositEntity.getBankIdentifier()==null)
            throw new DepositParameterIsNull("Значение параметра bankIdentifier равно null, ожидалось Long");
        if(depositEntity.getClientIdentifier()==null)
            throw new DepositParameterIsNull("Значение параметра clientIdentifier равно null, ожидалось Long");
        if(depositEntity.getOpenDate()==null)
            throw new DepositParameterIsNull("Значение параметра openDate равно null, ожидалось String в формате timestamp.");
        if(depositEntity.getPercentage()==null)
            throw new DepositParameterIsNull("Значение параметра percentage равно null, ожидалось Integer.");
        if(depositEntity.getMonthTerm()==null)
            throw new DepositParameterIsNull("Значение параметра monthTerm равно null, ожидалось Integer.");



        if(depositEntity.getMonthTerm()<0)
            throw new DepositMonthTermNotValid("Указанный monthTerm имеет неверный формат. Значение должно быть больше нуля, если вклад имеет срок, или равно нулю, если вклад бессрочный и иметь тип Long");
        if(depositEntity.getPercentage()<=0)
            throw new DepositPercentageNotValid("Указанный percentage имеет неверный формат. Значение должно быть больше нуля и иметь тип Long");


        if(!bankRepo.findById(depositEntity.getBankIdentifier()).isPresent())
            throw new DepositBankIdentifierNotFound("Указанный id банка не найден. Действие не произведено.");
        if(!clientRepo.findById(depositEntity.getClientIdentifier()).isPresent())
            throw new DepositClientIdentifierNotFound("Указанный id клиента не найден. Действие не произведено.");
    }
}
