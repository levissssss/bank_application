package com.epam.academy.homework.toy_bank.service;

import com.epam.academy.homework.toy_bank.dao.BankDao;
import com.epam.academy.homework.toy_bank.dao.ClientDao;
import com.epam.academy.homework.toy_bank.dao.PersonDao;
import com.epam.academy.homework.toy_bank.domain.Bank;
import com.epam.academy.homework.toy_bank.domain.Client;
import com.epam.academy.homework.toy_bank.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class BankingService {
    private BankDao bankDao;
    private ClientDao clientDao;
    private PersonDao personDao;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    public BankingService(BankDao bankDao, ClientDao clientDao, PersonDao personDao) {
        this.bankDao = bankDao;
        this.clientDao = clientDao;
        this.personDao = personDao;
    }

    @Transactional
    public void setInterestRates(BigDecimal lendingInterest, BigDecimal depositInterest) {
        Bank bank = new Bank();
        bank.setLendingInterest(lendingInterest);
        bank.setDepositInterest(depositInterest);
        bankDao.makeBank(bank);
    }

    @Transactional
    public void addClient(String firstName, String lastName, LocalDate dateOfBirth, BigDecimal initialBalance) {
        Client client = new Client();
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setDateOfBirth(dateOfBirth);
        client.setPerson(person);
        client.setAmountDeposited(initialBalance);
        personDao.save(person);
        clientDao.save(client);
        bankDao.addClient(client);
        bankDao.putInVault(initialBalance);
    }

    @Transactional
    public void lendMoneyToBank(BigDecimal amount, Person person) {
        Client client = clientDao.findByDetails(person);
        if (clientDao.clientCanAfford(client, amount)) {
            client.setAmountDeposited(client.getAmountDeposited().subtract(amount));
            client.setAmountLentToBank(client.getAmountLentToBank().add(amount));
        }
    }

    @Transactional
    public void lendMoneyToClient(BigDecimal amount, Person person) {
        if (bankDao.bankCanAfford(amount)) {
            Client client = clientDao.findByDetails(person);
            client.setAmountLentByBank(amount);
            client.setAmountDeposited(client.getAmountDeposited().add(amount));
        }
    }

    @Transactional
    public void payInterestsForDay() {
        List<Client> clients = clientDao.getAllClients();
        Bank bank = bankDao.getBank();
        for (Client client : clients) {
            payClient(bank, client);
            calculateClientNewDebt(bank, client);
        }
    }

    private void calculateClientNewDebt(Bank bank, Client client) {
        if (clientDao.clientHasBorrowedFromBank(client)) {
            BigDecimal interest = calculateLendingInterest(bank, client);
            client.setAmountLentByBank(client.getAmountLentByBank().add(interest));
        }
    }

    private BigDecimal calculateLendingInterest(Bank bank, Client client) {
        return client.getAmountLentByBank().multiply(bank.getLendingInterest());
    }

    private void payClient(Bank bank, Client client) {
        if (clientDao.clientHasLentToBank(client)) {
            BigDecimal interest = calculateDepositInterest(bank, client);
            if (bankDao.bankCanAfford(interest)) {
                client.setAmountDeposited(client.getAmountLentToBank().add(interest));
                bankDao.takeFromVault(interest);
            }
        }
    }

    private BigDecimal calculateDepositInterest(Bank bank, Client client) {
        return client.getAmountLentToBank().multiply(bank.getDepositInterest());
    }

}
