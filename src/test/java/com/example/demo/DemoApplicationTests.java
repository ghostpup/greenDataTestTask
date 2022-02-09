package com.example.demo;

import com.example.demo.entity.BankEntity;
import com.example.demo.entity.ClientEntity;
import com.example.demo.entity.DepositEntity;
import com.example.demo.repository.BankRepo;
import com.example.demo.repository.ClientRepo;
import com.example.demo.repository.DepositRepo;
import com.example.demo.service.BankService;
import com.example.demo.service.ClientService;
import com.example.demo.service.DepositService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	BankService bankService;

	@Autowired
	ClientService clientService;

	@Autowired
	DepositService depositService;

	@Autowired
	BankRepo bankRepo;

	@Autowired
	ClientRepo clientRepo;

	@Autowired
	DepositRepo depositRepo;

	@Test
	void testBanks() throws Exception{
		BankEntity bankEntity = new BankEntity();
		bankEntity.setId(1L);
		bankEntity.setName("Тестовый банк");
		bankEntity.setBankIdentifier("000000001");


		bankService.create(bankEntity);

		bankEntity.setId(2L);
		bankEntity.setName("Тестовый банк1");
		bankEntity.setBankIdentifier("000000002");

		bankService.create(bankEntity);

		BankEntity bank1 = bankRepo.findByName("Тестовый банк");

		Assertions.assertEquals("Тестовый банк", bank1.getName());
		Assertions.assertEquals("000000001",bank1.getBankIdentifier());

		BankEntity bank2 = bankRepo.findByName("Тестовый банк1");
		Assertions.assertEquals("Тестовый банк1", bank2.getName());
		Assertions.assertEquals("000000002",bank2.getBankIdentifier());

		bank1.setName("Тестовый банк2");
		bank1.setBankIdentifier("000000003");
		bankService.update(bank1);

		BankEntity bank3 = bankRepo.findByName("Тестовый банк2");
		Assertions.assertEquals("Тестовый банк2", bank3.getName());
		Assertions.assertEquals("000000003",bank3.getBankIdentifier());

		bankRepo.deleteAll();
	}

	@Test
	void testClients() throws Exception{
		ClientEntity clientEntity = new ClientEntity();
		clientEntity.setName("Тестовый клиент");
		clientEntity.setShortName("ТК");
		clientEntity.setAdress("Тестовый адрес");
		clientEntity.setOrgForm(1L);

		clientService.create(clientEntity);

		ClientEntity client1 = clientRepo.findByName("Тестовый клиент");

		Assertions.assertEquals("Тестовый клиент",client1.getName());
		Assertions.assertEquals("ТК",client1.getShortName());
		Assertions.assertEquals("Тестовый адрес",client1.getAdress());
		Assertions.assertEquals(1l,client1.getOrgForm());

		clientEntity.setName("Тестовый клиент1");
		clientEntity.setShortName("ТК1");
		clientEntity.setAdress("Тестовый адрес1");
		clientEntity.setOrgForm(2L);

		clientService.create(clientEntity);

		ClientEntity client2 = clientRepo.findByName("Тестовый клиент1");

		Assertions.assertEquals("Тестовый клиент1",client2.getName());
		Assertions.assertEquals("ТК1",client2.getShortName());
		Assertions.assertEquals("Тестовый адрес1",client2.getAdress());
		Assertions.assertEquals(2l,client2.getOrgForm());

		client1.setName("Тестовый клиент2");
		client1.setShortName("ТК2");
		client1.setAdress("Тестовый адрес2");
		client1.setOrgForm(3L);

		clientService.update(client1);

		ClientEntity client3 = clientRepo.findByName("Тестовый клиент2");

		Assertions.assertEquals("Тестовый клиент2",client1.getName());
		Assertions.assertEquals("ТК2",client1.getShortName());
		Assertions.assertEquals("Тестовый адрес2",client1.getAdress());
		Assertions.assertEquals(3l,client1.getOrgForm());

		clientRepo.deleteAll();
	}


	@Test
	void testDeposit() throws Exception{
		ClientEntity clientEntity = new ClientEntity();
		clientEntity.setName("Тестовый клиент");
		clientEntity.setShortName("ТК");
		clientEntity.setAdress("Тестовый адрес");
		clientEntity.setOrgForm(1L);

		clientService.create(clientEntity);

		ClientEntity client1 = clientRepo.findByName("Тестовый клиент");

		BankEntity bankEntity = new BankEntity();
		bankEntity.setId(1L);
		bankEntity.setName("Тестовый банк");
		bankEntity.setBankIdentifier("000000001");

		bankService.create(bankEntity);

		BankEntity bank1 = bankRepo.findByName("Тестовый банк");

		DepositEntity depositEntity = new DepositEntity();

		depositEntity.setBankIdentifier(bank1.getId());
		depositEntity.setClientIdentifier(client1.getId());

		depositEntity.setPercentage(10);
		depositEntity.setMonthTerm(12);

		depositService.create(depositEntity);

		DepositEntity deposit = depositRepo.findByClientIdentifierAndBankIdentifier(client1.getId(),bank1.getId());

		Assertions.assertEquals(client1.getId(),deposit.getClientIdentifier());
		Assertions.assertEquals(bank1.getId(),deposit.getBankIdentifier());
		Assertions.assertEquals(depositEntity.getPercentage(),deposit.getPercentage());
		Assertions.assertEquals(depositEntity.getMonthTerm(),deposit.getMonthTerm());

		

		depositRepo.deleteAll();
		clientRepo.deleteAll();
		bankRepo.deleteAll();
	}
}
