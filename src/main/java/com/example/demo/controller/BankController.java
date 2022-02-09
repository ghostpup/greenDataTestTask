package com.example.demo.controller;

import com.example.demo.entity.BankEntity;
import com.example.demo.exception.bank.*;
import com.example.demo.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banks")
public class BankController {

    @Autowired
    private BankService bankService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody BankEntity bank){
        try {
            bankService.create(bank);
            return ResponseEntity.ok("Новый банк успешно занесен в систему.");
        }catch (BankNameAlreadyExists | BankIdentifierAlreadyExists | BankIdentifierWrongFormat e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при создании банка");
        }
    }


    @PostMapping("/update")
    public ResponseEntity update(@RequestBody BankEntity bank){
        try {
            bankService.update(bank);
            return ResponseEntity.ok("Информация о банке успешно сохранена.");
        }catch ( BankIdentifierAndNameExistsCombine | BankIdentifierWrongFormat e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при создании банка");
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            bankService.deleteById(id);
            return ResponseEntity.ok("Банк успешно удален");
        }catch ( Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/")
    public ResponseEntity getBanks( @RequestParam(required= false, defaultValue="") String filterName,
                                    @RequestParam(required= false, defaultValue="") String filterValue,
                                    @RequestParam(required= false, defaultValue="") String orderBy,
                                    @RequestParam(required= false, defaultValue="") String orderDir) {
        try {
            return ResponseEntity.ok(bankService.getBanks(filterName,filterValue,orderBy,orderDir));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
