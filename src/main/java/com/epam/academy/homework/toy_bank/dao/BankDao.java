package com.epam.academy.homework.toy_bank.dao;

import com.epam.academy.homework.toy_bank.domain.Bank;
import com.epam.academy.homework.toy_bank.domain.Client;

import java.math.BigDecimal;

public interface BankDao {

    void makeBank(Bank bank);
    Bank getBank();

    default void addClient(Client client){
        getBank().addClient(client);
    }

    default void putInVault(BigDecimal amount){
        getBank().setVaultBalance(getBank().getVaultBalance().add(amount));
    }

    default void takeFromVault(BigDecimal amount){
        getBank().setVaultBalance(getBank().getVaultBalance().subtract(amount));
    }

    default boolean bankCanAfford(BigDecimal amount){
        return getBank().getVaultBalance().compareTo(amount) >= 0;
    }
}
