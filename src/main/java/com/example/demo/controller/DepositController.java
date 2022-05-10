package com.example.demo.controller;

import com.example.demo.entity.DepositEntity;
import com.example.demo.exception.deposit.DepositBankIdentifierNotFound;
import com.example.demo.exception.deposit.DepositClientIdentifierNotFound;
import com.example.demo.exception.deposit.DepositMonthTermNotValid;
import com.example.demo.exception.deposit.DepositNotFound;
import com.example.demo.exception.deposit.DepositParameterIsNull;
import com.example.demo.exception.deposit.DepositPercentageNotValid;
import com.example.demo.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/deposits")
public class DepositController {

  @Autowired
  private DepositService depositService;

  @PostMapping("/create")
  public ResponseEntity create(@RequestBody DepositEntity deposit) {
    try {
      depositService.create(deposit);
      return ResponseEntity.ok("Вы успешно создали вклад");
    } catch (DepositPercentageNotValid |
                 DepositBankIdentifierNotFound |
                 DepositMonthTermNotValid |
                 DepositParameterIsNull |
                 DepositClientIdentifierNotFound e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Произошла ошибка при запросе к вкладам");
    }
  }

  @PostMapping("/update")
  public ResponseEntity update(@RequestBody DepositEntity deposit) {
    try {
      depositService.update(deposit);
      return ResponseEntity.ok("Вы успешно сохранили информацию о вкладе");
    } catch (DepositPercentageNotValid |
                 DepositBankIdentifierNotFound |
                 DepositMonthTermNotValid |
                 DepositParameterIsNull |
                 DepositClientIdentifierNotFound |
                 DepositNotFound e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Произошла ошибка при запросе к вкладам");
    }
  }

  @PostMapping("/delete/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    try {
      depositService.deleteById(id);
      return ResponseEntity.ok("Вклад успешно удален");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/")
  public ResponseEntity getDeposits(@RequestParam(required = false, defaultValue = "") String filterName,
                                    @RequestParam(required = false, defaultValue = "") String filterValue,
                                    @RequestParam(required = false, defaultValue = "") String orderBy,
                                    @RequestParam(required = false, defaultValue = "") String orderDir) {
    try {
      return ResponseEntity.ok(depositService.getDeposits(filterName, filterValue, orderBy, orderDir));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
