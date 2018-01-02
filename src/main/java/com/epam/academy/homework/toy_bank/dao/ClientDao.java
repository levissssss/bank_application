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

    boolean clientCanAfford(Client client, BigDecimal amount);

    boolean clientHasBorrowedFromBank(Client client);

    boolean clientHasLentToBank(Client client);
}
