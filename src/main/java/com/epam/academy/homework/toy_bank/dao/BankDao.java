package com.epam.academy.homework.toy_bank.dao;

import com.epam.academy.homework.toy_bank.domain.Bank;
import com.epam.academy.homework.toy_bank.domain.Client;

import java.math.BigDecimal;

public interface BankDao {

    void makeBank(Bank bank);
    Bank getBank();

    void addClient(Client client);

    void putInVault(BigDecimal amount);

    void takeFromVault(BigDecimal amount);

    boolean bankCanAfford(BigDecimal amount);
}
