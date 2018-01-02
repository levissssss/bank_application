package com.epam.academy.homework.toy_bank.service;

import com.epam.academy.homework.toy_bank.dao.BankDao;
import com.epam.academy.homework.toy_bank.dao.ClientDao;
import com.epam.academy.homework.toy_bank.domain.Client;
import com.epam.academy.homework.toy_bank.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class TransferService {
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private BankDao bankDao;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Transactional
    public void transferMoney(Person sender, Person receiver, BigDecimal amount) {
        Client senderClient = clientDao.findByDetails(sender);
        Client receiverClient = clientDao.findByDetails(receiver);
        if (clientDao.clientCanAfford(senderClient, amount)) {
            senderClient.setAmountDeposited(senderClient.getAmountDeposited().subtract(amount));
            receiverClient.setAmountDeposited(receiverClient.getAmountDeposited().add(amount));
        }
    }

    @Transactional
    public void retrieveCash(Person person, BigDecimal amount) {
        Client client = clientDao.findByDetails(person);
        if (clientDao.clientCanAfford(client, amount) && bankDao.bankCanAfford(amount)) {
            client.setAmountDeposited(client.getAmountDeposited().subtract(amount));
            bankDao.takeFromVault(amount);
        }
    }

}
