package com.example.demo.controller;

import com.example.demo.entity.ClientEntity;
import com.example.demo.exception.client.ClientAdressAlreadyOccupied;
import com.example.demo.exception.client.ClientNameAlreadyExists;
import com.example.demo.exception.client.ClientNameAndAdressAlreadyExistsCombined;
import com.example.demo.exception.client.ClientParameterIsNull;
import com.example.demo.service.ClientService;
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
@RequestMapping("/clients")
public class ClientController {


  @Autowired
  private ClientService clientService;

  @PostMapping("/create")
  public ResponseEntity create(@RequestBody ClientEntity client) {
    try {
      clientService.create(client);
      return ResponseEntity.ok("Вы успешно создали клиента");
    } catch (ClientParameterIsNull | ClientNameAlreadyExists | ClientAdressAlreadyOccupied e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Произошла ошибка при запросе к клиентам");
    }
  }

  @PostMapping("/update")
  public ResponseEntity update(@RequestBody ClientEntity client) {
    try {
      clientService.update(client);
      return ResponseEntity.ok("Информация о клиенте успешно сохранена.");
    } catch (ClientParameterIsNull | ClientNameAndAdressAlreadyExistsCombined e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Произошла ошибка при создании клиента");
    }
  }

  @PostMapping("/delete/{id}")
  public ResponseEntity delete(@PathVariable Long id) {
    try {
      clientService.deleteById(id);
      return ResponseEntity.ok("Клиент успешно удален");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/")
  public ResponseEntity getClients(@RequestParam(required = false, defaultValue = "") String filterName,
                                   @RequestParam(required = false, defaultValue = "") String filterValue,
                                   @RequestParam(required = false, defaultValue = "") String orderBy,
                                   @RequestParam(required = false, defaultValue = "") String orderDir) {
    try {
      return ResponseEntity.ok(clientService.getClients(filterName, filterValue, orderBy, orderDir));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
