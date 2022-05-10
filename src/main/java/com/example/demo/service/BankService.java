package com.example.demo.service;

import com.example.demo.entity.BankEntity;
import com.example.demo.exception.bank.BankEntityParameterIsNull;
import com.example.demo.exception.bank.BankHasDeposits;
import com.example.demo.exception.bank.BankIdentifierAlreadyExists;
import com.example.demo.exception.bank.BankIdentifierAndNameExistsCombine;
import com.example.demo.exception.bank.BankIdentifierWrongFormat;
import com.example.demo.exception.bank.BankNameAlreadyExists;
import com.example.demo.exception.bank.BankSortOrFilterParamsNotValid;
import com.example.demo.repository.BankRepo;
import com.example.demo.repository.DepositRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BankService {

  @Autowired
  private BankRepo bankRepo;

  @Autowired
  private DepositRepo depositRepo;

  //Методы CRUD
  public BankEntity create(BankEntity bankEntity)
          throws BankIdentifierAlreadyExists, BankNameAlreadyExists,
                         BankIdentifierWrongFormat, BankEntityParameterIsNull {
    if (bankEntity.getName() == null) {
      throw new BankEntityParameterIsNull("Значение параметра name равно null. Ожидалась строка.");
    }
    if (bankEntity.getBankIdentifier() == null) {
      throw new BankEntityParameterIsNull("Значение параметра bankIdentifier равно null. Ожидалась строка.");
    }
    if (!checkUniqueBankIdentifier(bankEntity.getBankIdentifier())) {
      throw new BankIdentifierAlreadyExists("Банк с таким БИК уже пристусвует в системе.");
    }
    if (!checkUniqueBankName(bankEntity.getName())) {
      throw new BankNameAlreadyExists("Банк с таким названием уже пристусвует в системе.");
    }
    if (!checkFormatBankIdentifier(bankEntity.getBankIdentifier())) {
      throw new BankIdentifierWrongFormat("Неверный формат БИК. Ожидалась строка, состоящая из 9 цифр.");
    }
    return bankRepo.save(bankEntity);
  }

  public BankEntity update(BankEntity bankEntity)
          throws BankIdentifierWrongFormat, BankIdentifierAndNameExistsCombine, BankEntityParameterIsNull {
    if (bankEntity.getId() == null) {
      throw new BankEntityParameterIsNull("Значение параметра id равно null.");
    }
    if (bankEntity.getName() == null) {
      throw new BankEntityParameterIsNull("Значение параметра name равно null. Ожидалась строка.");
    }
    if (bankEntity.getBankIdentifier() == null) {
      throw new BankEntityParameterIsNull("Значение параметра bankIdentifier равно null. Ожидалась строка.");
    }
    if (!checkFormatBankIdentifier(bankEntity.getBankIdentifier())) {
      throw new BankIdentifierWrongFormat("Неверный формат БИК. Ожидалась строка, состоящая из 9 цифр.");
    }
    if (!checkSameValuesInOtherEntries(bankEntity)) {
      throw new BankIdentifierAndNameExistsCombine("В системе существуют банки, которорые имеют такое же имя и/или БИК.");
    }
    return bankRepo.save(bankEntity);
  }

  public void deleteById(Long id) throws BankHasDeposits {
    if (depositRepo.findByBankIdentifier(id) != null) {
      throw new BankHasDeposits("Вы не можете удалить банк, т.к. в нем присутсвуют активные вклады.");
    }
    bankRepo.deleteById(id);
  }

  public Iterable<BankEntity> getBanks(String criteriaName, String criteriaValue,
                                       String orderBy, String orderDirection)
      throws BankSortOrFilterParamsNotValid {
    String[] fields = {"id", "name", "bankIdentifier"};
    if ((!criteriaName.isEmpty() && !Arrays.asList(fields).contains(criteriaName)) ||
                (!orderBy.isEmpty() && !Arrays.asList(fields).contains(orderBy))) {
      throw new BankSortOrFilterParamsNotValid("Неверные названия параметров фильтрации и сортировки." +
                                               " Доступные значения: " + Arrays.toString(fields));
    }
    return bankRepo.findAndSortByCriteria(criteriaName, criteriaValue, orderBy, orderDirection);
  }

  //Методы проверки
  private Boolean checkUniqueBankName(String bankName) {
    return bankRepo.findByName(bankName) == null;
  }

  private Boolean checkUniqueBankIdentifier(String bankIdentifier) {
    return bankRepo.findByBankIdentifier(bankIdentifier) == null;
  }

  private Boolean checkFormatBankIdentifier(String bankIdentifier) {
    return bankIdentifier.matches("[0-9]+") && bankIdentifier.length() == 9;
  }

  private Boolean checkSameValuesInOtherEntries(BankEntity bankEntity) {
    return bankRepo.findAllByBankIdentifierOrNameAndIdNot(
                    bankEntity.getId(),
                    bankEntity.getBankIdentifier(),
                    bankEntity.getName())
            .isEmpty();
  }
}
