package com.epam.academy.homework.toy_bank.dao;

import com.epam.academy.homework.toy_bank.domain.Client;
import com.epam.academy.homework.toy_bank.domain.Person;

import java.math.BigDecimal;
import java.util.List;

public interface ClientDao {

    void save(Client client);

    Client findByFirstName(String firstName);
    Client findByDetails(Person toSearch);
    List<Client> getAllClients();

    default boolean clientCanAfford(Client client, BigDecimal amount){
        return client.getAmountDeposited().compareTo(amount) >= 0;
    }

    default boolean clientHasBorrowedFromBank(Client client){
        return client.getAmountLentByBank().compareTo(BigDecimal.valueOf(0)) > 0;
    }

    default boolean clientHasLentToBank(Client client){
        return client.getAmountLentToBank().compareTo(BigDecimal.valueOf(0)) > 0;
    }
}
