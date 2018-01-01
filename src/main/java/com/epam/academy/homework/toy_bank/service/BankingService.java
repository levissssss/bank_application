package com.epam.academy.homework.toy_bank.service;

import com.epam.academy.homework.toy_bank.dao.BankDao;
import com.epam.academy.homework.toy_bank.dao.ClientDao;
import com.epam.academy.homework.toy_bank.dao.PersonDao;
import com.epam.academy.homework.toy_bank.domain.Bank;
import com.epam.academy.homework.toy_bank.domain.Client;
import com.epam.academy.homework.toy_bank.domain.Person;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BankingService {
    private BankDao bankDao;
    private ClientDao clientDao;
    private PersonDao personDao;
    @Resource
    private PlatformTransactionManager platformTransactionManager;

    public BankingService(BigDecimal lendingInterest, BigDecimal depositInterest) {
        Bank bank = new Bank();
        bank.setLendingInterest(lendingInterest);
        bank.setDepositInterest(depositInterest);
        bankDao.makeBank(bank);
    }

    @Transactional
    public void lendMoney(BigDecimal amount, Person person) {
        Bank bank = bankDao.getBank();
        if (bankCanAfford(amount, bank)) {
            Client client = clientDao.findByDetails(person);
            client.setAmountLentByBank(amount);
            bank.setVaultBalance(bank.getVaultBalance().subtract(amount));
            clientDao.updateClient(client);
            bankDao.updateBank(bank);
        }
    }

    private boolean bankCanAfford(BigDecimal amount, Bank bank) {
        return bank.getVaultBalance().compareTo(amount) > 0;
    }

    @Transactional
    public void payInterestsForDay() {
        List<Client> clients = clientDao.getAllClients();
        Bank bank = bankDao.getBank();
        for (Client client : clients) {
            payClient(bank, client);
            calculateClientNewDebt(bank, client);
            clientDao.updateClient(client);
        }
        bankDao.updateBank(bank);
    }

    private void calculateClientNewDebt(Bank bank, Client client) {
        if (clientHasBorrowedFromBank(client)) {
            BigDecimal interest = calculateLendingInterest(bank, client);
            client.setAmountLentByBank(client.getAmountLentByBank().add(interest));
        }
    }

    private BigDecimal calculateLendingInterest(Bank bank, Client client) {
        return client.getAmountLentByBank().multiply(bank.getLendingInterest());
    }

    private boolean clientHasBorrowedFromBank(Client client) {
        return client.getAmountLentByBank().compareTo(BigDecimal.valueOf(0)) > 0;
    }

    private void payClient(Bank bank, Client client) {
        if (clientHasLentToBank(client)) {
            BigDecimal interest = calculateDepositInterest(bank, client);
            if (bankCanAfford(interest, bank)) {
                client.setAmountDeposited(client.getAmountLentToBank().add(interest));
                bank.setVaultBalance(bank.getVaultBalance().subtract(interest));
            }
        }
    }

    private BigDecimal calculateDepositInterest(Bank bank, Client client) {
        return client.getAmountLentToBank().multiply(bank.getDepositInterest());
    }

    private boolean clientHasLentToBank(Client client) {
        return client.getAmountLentToBank().compareTo(BigDecimal.valueOf(0)) > 0;
    }

    public void addClient(String firstName, String lastName, LocalDate dateOfBirth, BigDecimal initialBalance) {
        Client client = new Client();
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setDateOfBirth(dateOfBirth);
        client.setPerson(person);
        client.setAmountDeposited(initialBalance);
        personDao.save(person);
        clientDao.newClient(client);
    }
}
